package com.ycspl.bingaa.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ycspl.bingaa.R


@Composable
fun PhotoDocumentCard(
    modifier: Modifier = Modifier
        .height(66.dp)
        .width(100.dp),
    onDeleteClicked: () -> Unit,
    img: Painter = painterResource(id = R.drawable.beautiful_nature),
    fileName: String = "image file"
) {
    val openDialog = remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
    ) {
        Image(
            painter = img,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(48.dp)
                .width(72.02999877929688.dp)
                .align(Alignment.BottomStart)
                .padding(top = 6.dp)
                .clip(RoundedCornerShape(3.dp))
        )

        IconButton(
            onClick = {
                openDialog.value = true
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(start = 10.dp)
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_red_close),
                contentDescription = null
            )
        }
    }

    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.floor_details_button_delete))
            },
            text = {
                Text(
                    stringResource(
                        R.string.photo_document_dialog_delete_floor,
                        fileName
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDeleteClicked()
                    }) {
                    Text(stringResource(R.string.photo_document_dialog_delete_button_suggest))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(stringResource(R.string.photo_document_dialog_delete_button_no_suggest))
                }
            }
        )
    }
}

@Preview
@Composable
fun PhotoDocumentPreview() {
    PhotoDocumentCard(onDeleteClicked = {

    })
}