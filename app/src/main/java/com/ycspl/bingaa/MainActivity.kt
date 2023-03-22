package com.ycspl.bingaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.ycspl.bingaa.ui.theme.BingAATheme
import dagger.hilt.android.AndroidEntryPoint

data class UserModel(val name: String, val age: Int, val address: String)
data class Wrapper(val id: Int, val name:String)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingAATheme {

                val viewModel : MainViewModel by viewModels<MainViewModel>()
                var selectedStateIdViewModel = viewModel.mutableSelectedState.collectAsState()
                var selectedCityIdViewModel = viewModel.selectedCity.collectAsState()
                val stateItem = StateItem(id = -1, name = "Select State", cities = mutableListOf(City(id = -1, name = "Select City", state_id = -1)))

                val selectedState = remember {
                    mutableStateOf<StateItem>(stateItem)
                }

                val allStates = mutableListOf<StateItem>(stateItem)
                val states = JsonParser.getListOfStates(LocalContext.current)
                allStates.addAll(states)

                val database = PersonDatabase.getInstance(context = LocalContext.current)
                val details = database.getDao().getPersonById(2)
                details?.let {
                    viewModel.changeSelectedCity(details.city!!)
                    viewModel.changeSelectedState(details.state!!)
                }



                var selectedCity by remember {
                    mutableStateOf(
                        SelectWrapper(-1, "")
                    )
                }

                val stateSelectWrapper = allStates.map { SelectWrapper(id = it.id, name = it.name) }

                val citiesSelectWrapper = mutableListOf<SelectWrapper>()
                allStates.forEach {
                    it.cities.map {
                        citiesSelectWrapper.add(SelectWrapper(
                            id = it.id,
                            name = it.name,
                            bind_id = it.state_id
                        ))
                    }
                }

                val location by viewModel.location.collectAsState()


                AnimatedVisibility(visible = allStates.isNotEmpty() && location != null) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        DropDownMenuSelectWrapper(
                            suggestions = stateSelectWrapper,
                            title = "States",
                            { selectedID->
                                val filteredList = states.filter { it.id == selectedID.toInt() }
                                if (filteredList.isEmpty()) {
                                    selectedState.value = stateItem //assign default item
                                } else {
                                    selectedState.value = filteredList?.first() //assign selected item
                                }
                                viewModel.changeSelectedState(selectedState.value.name)
                            },
                            selectIdStr = getId(selectedStateIdViewModel.value,stateSelectWrapper),
                        )

                        citiesSelectWrapper.let { list->
                            var suggestions = list.filter { it.bind_id == selectedState.value.id }

                            Log.d("TAG", "onCreate: suggestions ${suggestions.size}")

                            if (suggestions.isEmpty()) suggestions = listOf(SelectWrapper(-1, "Empty"))

                            selectedCity = if (selectedCityIdViewModel.value != "null") suggestions.find {
                                    it.id == getId(selectedCityIdViewModel.value, suggestions)?.toInt()
                                } ?: suggestions[0] else suggestions[0]
                            DropDownMenuSelectWrapperOrEmpty(
                                suggestions = suggestions,
                                title = "Cities",{id ->
                                    viewModel.changeSelectedCity(suggestions.filter { it.id == id.toInt() }.first().name)
                                }, {
                                    selectedCity = it
                                },
                                selectedCity
                            )
                        }


                        Spacer(modifier = Modifier.height(20.dp))

                        AnimatedVisibility(visible = location != null) {
                            Column {
                                Text(text = "Selected State is ${location?.state} $selectedStateIdViewModel")
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(text = "Selected City is ${location?.city} $selectedCityIdViewModel" )
                            }
                        }


                        Button(onClick = {
                            viewModel.saveLocation(
                                selectedState.value.name,
                                selectedCity.name
                            )
                        }) {
                            Text("Save Current Location")
                        }

                    }
                }

            }
        }
    }

    private fun getId(
        selectedStateIdViewModel: String,
        stateSelectWrapper: List<SelectWrapper>
    ): String? {
        val filteredList = stateSelectWrapper.filter { it.name == selectedStateIdViewModel }
        return if(filteredList.isEmpty()) "0" else filteredList.first().id.toString()
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuString(
    suggestions: List<String>,
    title: String,
    selectIdStr: String? = null,
    onSelectChanged: (String) -> Unit,
    notSelectId: String? = null
) {
    var selectedText by remember {
        mutableStateOf(
            if (selectIdStr != null) {
                if (selectIdStr != "")
                    suggestions[selectIdStr.toInt()]
                else
                    suggestions[0]
            } else
                suggestions[0]
        )
    }

    if (selectIdStr == notSelectId)
        selectedText = suggestions[0]

    var expanded by remember { mutableStateOf(false) }

    onSelectChanged(suggestions.indexOf(selectedText).toString() ?: "")

    Column() {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {

            var textfieldSize by remember { mutableStateOf(Size.Zero) }

            OutlinedTextField(
                readOnly = true,
                value = selectedText,
                onValueChange = { },
                label = { Text(title, modifier = Modifier, style = TextStyle(fontSize = 12.sp)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .exposedDropdownSize(),
                properties = PopupProperties(
                    focusable = true,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                ),
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                        onSelectChanged(suggestions.indexOf(label).toString() ?: "0")
                        expanded = false
                    }) {
                        if (selectedText == label) {
                            Text(
                                text = label,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                ),
                            )

                        } else
                            Text(text = label)
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuSelectWrapper(
    suggestions: List<SelectWrapper>,
    title: String,
    onPropertySelectChanged: (String) -> Unit,
    selectIdStr: String? = null,
    notSelectId: String? = null,
    notSelectIndex: String? = null,
    placeholder:String?=null,
    edit: Boolean = false
) {
    var selectedText by remember {
        mutableStateOf(
            suggestions.find { it.id == selectIdStr?.toInt() } ?: suggestions[0]
        )
    }

    Log.d("TAG", "DropDownMenuSelectWrapper: $selectIdStr $notSelectId $selectedText")

    if (selectIdStr == notSelectId) {
        selectedText = if (notSelectIndex !== null)
            suggestions[notSelectIndex.toInt()]
        else
            suggestions[0]
    } else {
        if(edit) {
            selectIdStr?.let {
                suggestions.find { it.id == selectIdStr?.toInt() }?.let {
                    selectedText = it
                } ?: run {
                    return
                }
            }
        }
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedOnce by remember { mutableStateOf(false) }

    onPropertySelectChanged(selectedText.id.toString())

    Column() {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {

            var textfieldSize by remember { mutableStateOf(Size.Zero) }

            OutlinedTextField(
                readOnly = true,
                value = if (placeholder != null && selectedOnce.not()) placeholder else if (selectedText.name != "Not selected") selectedText.name else "",
                onValueChange = { },
                label = {
                    Text(
                        title,
                        modifier = Modifier,
                        style = TextStyle(fontSize = 10.sp)
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                textStyle = TextStyle.Default.copy(fontSize = 12.sp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .exposedDropdownSize(),
                properties = PopupProperties(
                    focusable = true,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                ),
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        if(selectedOnce.not()){
                            selectedOnce=true
                        }
                        selectedText = label
                        expanded = false
                        onPropertySelectChanged(selectedText.id.toString())
                    }) {
                        if (selectedText == label) {
                            Text(
                                text = label.name,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        } else
                            Text(text = label.name)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuSelectWrapperOrEmpty(
    suggestions: List<SelectWrapper>,
    title: String,
    onIdStringSelectChanged: (String) -> Unit,
    onSelectWrapperSelectChanged: (SelectWrapper) -> Unit,
    select: SelectWrapper,
) {
    var expanded by remember { mutableStateOf(false) }

    onIdStringSelectChanged(select.id.toString())

    Column() {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {

            var textfieldSize by remember { mutableStateOf(Size.Zero) }

            OutlinedTextField(
                readOnly = true,
                value = if (select.id != -1) select.name else "",
                onValueChange = { },
                label = { Text(title, modifier = Modifier, style = TextStyle(fontSize = 12.sp)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                textStyle = TextStyle.Default.copy(fontSize = 12.sp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .exposedDropdownSize(),
                properties = PopupProperties(
                    focusable = true,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                ),
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        onSelectWrapperSelectChanged(label)
                        expanded = false
                        onIdStringSelectChanged(label.id.toString())
                    }) {
                        if (select == label) {
                            Text(
                                text = label.name,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        } else
                            Text(text = label.name)
                    }
                }
            }
        }
    }
}