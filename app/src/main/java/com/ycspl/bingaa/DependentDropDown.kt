package com.ycspl.correctionapp.common.uicomponents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ycspl.bingaa.DropDownMenuModel


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DependentDropDown(title: String, items: List<DropDownMenuModel>, onSelection: (Int) -> Unit, selectedText: String = "") {
    val TAG = "CommonDropDownButton"
    var expanded by remember { mutableStateOf(false) }
    var changed by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0].name) }
    val currentSelectedValue = mutableStateOf(items[0].name)


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        Log.d(TAG, "DependentDropDown: selectedOptionText :$selectedOptionText currentSelectedValue:${currentSelectedValue.value}")

        Box(contentAlignment = Alignment.Center) {
            BasicTextField(
                readOnly = selectedOptionText.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(5.dp))
                    .height(40.dp)
                    .padding(10.dp),
                value = if (changed) selectedOptionText else currentSelectedValue.value,
                onValueChange = {},
                textStyle = TextStyle(
                    color = if (isSystemInDarkTheme()) Color(0xFF969EBD) else Color(0xff000000)
                ),

//            keyboardOptions = keyboardOptions,
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (selectedOptionText.isEmpty()) {
                            Text(
                                text = "placeholder",
                                color = if (isSystemInDarkTheme()) Color(0xFF969EBD) else Color(
                                    0x40000000
                                ),
                                fontSize = 14.sp
                            )
                        }
                    }
                    innerTextField()
                }
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
        }

        ExposedDropdownMenu(
            expanded = expanded, modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                expanded = false
            }
        ) {
            items.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        currentSelectedValue.value = selectedOptionText
                        selectedOptionText = selectionOption.name
                        expanded = false
                        changed = true
                        onSelection(selectionOption.id)
                    }
                ) {
                    Text(
                        text = selectionOption.name,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
