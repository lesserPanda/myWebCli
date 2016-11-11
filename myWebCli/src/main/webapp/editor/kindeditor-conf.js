$(function(){
	editorArray = new Array();
	$("textarea.editor").each(function(){
		var editor = $(this).attr("class");
		var name = $(this).attr("name");
		if(name.indexOf(".") != -1){
			name = name.replace(".","_");
		}
		$(this).attr("id",name);
		
		if(editor.indexOf("basic") != -1){
			editorArray.push(KindEditor.create('#'+name, {
				allowPreviewEmoticons : false,
				allowImageUpload : true,
				items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link']
			})); 
		}else{
			editorArray.push(KindEditor.create('#'+name, {
	            uploadJson : '/editor/kind/upload.do',
	            fileManagerJson : '/editor/kind/manage.do',
	            allowFileManager : true
			}));
		}
	});
});
