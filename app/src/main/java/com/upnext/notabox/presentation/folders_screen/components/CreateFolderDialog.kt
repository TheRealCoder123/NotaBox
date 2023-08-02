package com.upnext.notabox.presentation.folders_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.upnext.notabox.data.enitities.Folder
import com.upnext.notabox.domain.model.CreateFolder
import com.upnext.notabox.presentation.ui.theme.NotaBoxTheme

@Composable
fun CreateFolderDialog(
    isDialogVisible: Boolean,
    onDismiss: () -> Unit,
    onCreate: (folderName:String, isRenaming:Boolean) -> Unit,
    isRenamingFolder: Boolean = false,
) {

    var folderNameText by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current


    if(isDialogVisible){
        Dialog(onDismissRequest = {
            folderNameText = ""
            onDismiss()
        }) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(NotaBoxTheme.colors.dialogBgColor)
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(NotaBoxTheme.spaces.large),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = folderNameText,
                        onValueChange = { newText -> folderNameText = newText },
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            color = NotaBoxTheme.colors.text,
                            fontSize = 20.sp
                        ),
                        decorationBox = {
                            if (folderNameText.isEmpty()){
                                Text(
                                    text = "Folder Name",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                            }
                            it()
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(NotaBoxTheme.spaces.large))

                    OutlinedButton(
                        onClick = {
                            if (folderNameText.isNotEmpty()){
                                onCreate(folderNameText, isRenamingFolder)
                                folderNameText = ""
                            }else{
                                Toast.makeText(context, "Please enter name first", Toast.LENGTH_SHORT).show()
                            }
                        },
                    ) {
                        Text(
                            text = if (isRenamingFolder) "Rename Folder" else "Create Folder",
                            color = NotaBoxTheme.colors.text
                        )
                    }


                }

            }
        }
    }



}