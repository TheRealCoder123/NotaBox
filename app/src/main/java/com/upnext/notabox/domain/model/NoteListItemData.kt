package com.upnext.notabox.domain.model

import com.upnext.notabox.data.enitities.Note
import com.upnext.notabox.data.enitities.Priority
import com.upnext.notabox.domain.enums.NoteDataType

data class NoteListItemData(
    val id: String,
    val title: String,
    val timestamp: Long,
    val priority: Priority?,
    val folderId: Int,
    val image: String?,
    val hasAudioFiles: Boolean,
    val hasMindMap: Boolean,
    val hasCheckBoxList: Boolean,
    val hasFile: Boolean,
    val textNoteData: List<NoteData>
) {

   companion object {

       fun Note.toNoteListItem(): NoteListItemData {

           var hasAudioFiles = false
           val hasMindMap = false
           var hasCheckBoxList = false
           var hasFile = false
           var texts = emptyList<NoteData>()

           val imageList = noteData.filter { it.type == NoteDataType.Image }
           val image = if (imageList.isNotEmpty()) imageList[0].imageUri else null
           if(noteData.any { it.type == NoteDataType.Text }){
               texts = noteData.filter { it.type == NoteDataType.Text }
           }
           hasFile = noteData.any { it.type == NoteDataType.File }
           hasCheckBoxList = noteData.any { it.type == NoteDataType.CheckBoxList }
           hasAudioFiles = noteData.any { it.type == NoteDataType.Audio }


           return NoteListItemData(
               id,
               title,
               timestamp,
               priority,
               folderId,
               image,
               hasAudioFiles,
               hasMindMap,
               hasCheckBoxList,
               hasFile,
               texts
           )
       }
   }

}
