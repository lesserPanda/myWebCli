function openOffice( classid, codebase )
{
	var str = '';
	str += '<div id="docDiv">';
	str += '<object id="WebOffice" width="100%" height="1000" classid="'+classid+'" codebase="'+codebase+'">';
	str += '</object>';
	str += '</div>';
	document.write(str);
}
//作用：显示操作状态
function StatusMsg(mString){
  //alert(mString);
}
//作用：退出iWebOffice
function UnLoad(){
  try{
  if (!webform.WebOffice.WebClose()){
     StatusMsg(webform.WebOffice.Status);
  }else{
     StatusMsg("关闭文档...");
  }
  }catch(e){}
}

//作用：打开文档
function LoadDocument(){
  StatusMsg("正在打开文档...");
  if (!webform.WebOffice.WebOpen()){  	//打开该文档    交互OfficeServer的OPTION="LOADFILE"
     StatusMsg(webform.WebOffice.Status);
  }else{
     StatusMsg(webform.WebOffice.Status);
  }
}

//作用：保存文档
function SaveDocument(){
  if (!webform.WebOffice.WebSave()){    //交互OfficeServer的OPTION="SAVEFILE"  注：WebSave()是保存复合格式文件，包括OFFICE内容和手写批注文档；如只保存成OFFICE文档格式，那么就设WebSave(true)
     StatusMsg(webform.WebOffice.Status);
     return false;
  }else{
     StatusMsg(webform.WebOffice.Status);
     return true;
  }
}


//作用：显示或隐藏痕迹[隐藏痕迹时修改文档没有痕迹保留]  true表示隐藏痕迹  false表示显示痕迹
function ShowRevision(mValue){
  if (mValue){
     webform.WebOffice.WebShow(true);
     StatusMsg("显示痕迹...");
  }else{
     webform.WebOffice.WebShow(false);
     StatusMsg("隐藏痕迹...");
  }
}


//作用：显示或隐藏痕迹[隐藏痕迹时修改文档有痕迹保留]  true表示隐藏痕迹  false表示显示痕迹
function ShowRevision2(mValue){
  if (mValue){
     webform.WebOffice.WebObject.ShowRevisions=true;   //显示痕迹
  }else{
     webform.WebOffice.WebObject.ShowRevisions=false;  //隐藏痕迹
  }
}


//作用：获取痕迹
function WebGetRevisions(){
  var i;
  var Text="";
  for (i = 1;i <= webform.WebOffice.WebObject.Revisions.Count;i++){
    Text=Text + webform.WebOffice.WebObject.Revisions.Item(i).Author;
    if (webform.WebOffice.WebObject.Revisions.Item(i).Type=="1"){
      Text=Text + '插入:'+webform.WebOffice.WebObject.Revisions.Item(i).Range.Text+"\r\n";
    }else{
      Text=Text + '删除:'+webform.WebOffice.WebObject.Revisions.Item(i).Range.Text+"\r\n";
    }
  }
  var md=window.showModelessDialog("empty.html",Text,"dialogHeight:20;scoll:yes;dialogWidth:20;help:no");
}

//作用：刷新文档
function WebReFresh(){
  webform.WebOffice.WebReFresh();
  StatusMsg("文档已刷新...");
}


//作用：打开版本
function WebOpenVersion(){
  webform.WebOffice.WebOpenVersion();  	//交互OfficeServer  列出版本OPTION="LISTVERSION"     调出版本OPTION="LOADVERSION"   <参考技术文档>
  StatusMsg(webform.WebOffice.Status);
}

//作用：保存版本
function WebSaveVersion(){
  webform.WebOffice.WebSaveVersion();  	//交互OfficeServer的OPTION="SAVEVERSION"
  StatusMsg(webform.WebOffice.Status);

}

//作用：保存当前版本
function WebSaveVersionByFileID(){
  var mText=window.prompt("请输入版本说明:","版本号:V");
  if (mText==null){
     mText="已修改版本.";
  }
  webform.WebOffice.WebSaveVersionByFileID(mText);  	//交互OfficeServer的OPTION="SAVEVERSION"  同时带FileID值   <参考技术文档>
  StatusMsg(webform.WebOffice.Status);
}


//作用：填充模板
function LoadBookmarks(){
  StatusMsg("正在填充模扳...");
  if (!webform.WebOffice.WebLoadBookmarks()){  	//交互OfficeServer的OPTION="LOADBOOKMARKS"
     StatusMsg(webform.WebOffice.Status);
  }else{
     StatusMsg(webform.WebOffice.Status);
  }
}

//作用：标签管理
function WebOpenBookMarks(){
  try{
    webform.WebOffice.WebOpenBookmarks();  	//交互OfficeServer的OPTION="LISTBOOKMARKS"
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}

//作用：设置书签值  vbmName:标签名称，vbmValue:标签值   标签名称注意大小写
function SetBookmarks(vbmName,vbmValue){
  if (!webform.WebOffice.WebSetBookmarks(vbmName,vbmValue)){
     StatusMsg(webform.WebOffice.Status);
  }else{
     StatusMsg(webform.WebOffice.Status);
  }
}

//作用：根据标签名称获取标签值  vbmName:标签名称
function GetBookmarks(vbmName){
  var vbmValue;
  vbmValue=webform.WebOffice.WebGetBookmarks(vbmName);
  return vbmValue;
}

//作用：打印文档
function WebOpenPrint(){
  try{
    webform.WebOffice.WebOpenPrint();
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}


//作用：页面设置
function WebOpenPageSetup(){
   try{
	if (webform.WebOffice.FileType==".doc"){
	  webform.WebOffice.WebObject.Application.Dialogs(178).Show();
	}
	if(webform.WebOffice.FileType==".xls"){
	  webform.WebOffice.WebObject.Application.Dialogs(7).Show();
	}
   }catch(e){}
}

//作用：插入图片
function WebOpenPicture(){
  try{
    webform.WebOffice.WebOpenPicture();
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}

//作用：签名印章
function WebOpenSignature(){
  //alert("如果你要更安全签章,建议采用金格iSignature电子签章软件进行签章:\r\n下载地址http://www.goldgrid.cn/iSignature/Download.asp\r\n\r\n该软件是支持文档完整性保护、CA证书和数字签名技术的,\r\n是通过了<国家公安部和国家保密局>双重认证的安全电子签章产品!");
  try{
    webform.WebOffice.WebOpenSignature();  	//交互OfficeServer的 A签章列表OPTION="LOADMARKLIST"    B签章调出OPTION="LOADMARKIMAGE"    C确定签章OPTION="SAVESIGNATURE"    <参考技术文档>
    
    alert(webform.WebOffice.WebObject.ActiveWindow.Selection.Type);
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}

//作用：验证印章A
function WebShowSignature(){
  try{
    webform.WebOffice.WebShowSignature();  	//交互OfficeServer的OPTION="LOADSIGNATURE"
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}

//作用：验证印章B
function WebCheckSignature(){
  try{
    var i=webform.WebOffice.WebCheckSignature();  	//交互OfficeServer的OPTION="LOADSIGNATURE"
    alert("检测结果："+i+"\r\n 注释: (=-1 有非法印章) (=0 没有任何印章) (>=1 有多个合法印章)");
    StatusMsg(i);
  }catch(e){}
}

//作用：存为本地文件
function WebSaveLocal(){
  try{
    //webform.WebOffice.WebShow(false);  
	webform.WebOffice.WebSetProtect(false,"");  
    webform.WebOffice.WebSaveLocal();
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}

//作用：打开本地文件
function WebOpenLocal(){
  try{
    webform.WebOffice.WebOpenLocal();
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}



//作用：关闭或显示工具 参数1表示工具条名称  参数2为false时，表示关闭  （名称均可查找VBA帮助）
//参数2为true时，表示显示
function WebToolsVisible(ToolName,Visible){
  try{
    webform.WebOffice.WebToolsVisible(ToolName,Visible);
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}


//作用：禁止或启用工具 参数1表示工具条名称  参数2表示工具条铵钮的编号  （名称和编号均可查找VBA帮助）
//参数3为false时，表示禁止  参数3为true时，表示启用
function WebToolsEnable(ToolName,ToolIndex,Enable){
  try{
    webform.WebOffice.WebToolsEnable(ToolName,ToolIndex,Enable);
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}

//作用：保护与解除  参数1为true表示保护文档  false表示解除保护
function WebProtect(value){
  try{
    webform.WebOffice.WebSetProtect(value,"");  //""表示密码为空
  }catch(e){}
}

//作用：允许与禁止拷贝功能  参数1为true表示允许拷贝  false表示禁止拷贝
function WebEnableCopy(value){
  try{
    webform.WebOffice.CopyType=value;
  }catch(e){}
}


//作用：插入远程服务器图片
function WebInsertImage(){
  try{
    webform.WebOffice.WebInsertImage('Image','GoldgridLogo.jpg',true,4);   //交互OfficeServer的OPTION="INSERTIMAGE"  参数1表示标签名称  参数2表示图片文件名  参数3为true透明  false表示不透明  参数4为4表示浮于文字上方  5表示衬于文字下方
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
}


//作用：下载服务器文件到本地
function WebGetFile(){
  if (webform.WebOffice.WebGetFile("c:\\WebGetFile.doc","DownLoad.doc")){   //交互OfficeServer的OPTION="GETFILE"  参数1表示本地路径  参数2表示服务器文件名称
    StatusMsg(webform.WebOffice.Status);
  }else{
    StatusMsg(webform.WebOffice.Status);
  }
  alert(webform.WebOffice.Status+"\r\n"+"文件放在c:\\WebGetFile.doc");
}


//作用：上传本地文件到服务器
function WebPutFile(){
  var mLocalFile=webform.WebOffice.WebOpenLocalDialog();
  if (mLocalFile!=""){
    alert(mLocalFile);
    if (webform.WebOffice.WebPutFile(mLocalFile,"Test.doc")){   //交互OfficeServer的OPTION="PUTFILE"  参数1表示本地路径，可以任何格式文件  参数2表示服务器文件名称
      StatusMsg(webform.WebOffice.Status);
    }else{
      StatusMsg(webform.WebOffice.Status);
    }
    alert(webform.WebOffice.Status);
  }
}


//作用：打开远程文件
function WebDownLoadFile(){
  mResult=webform.WebOffice.WebDownLoadFile("http://www.goldgrid.com/Images/abc.doc","c:\\abc.doc");
  if (mResult){
    webform.WebOffice.WebOpenLocalFile("c:\\abc.doc");
    alert("成功");
  }else{
    alert("失败");
  }
}

//作用：取得服务器端时间，设置本地时间  [V6.0.1.5以上支持]
function WebDateTime(){
  mResult=webform.WebOffice.WebDateTime(true);   //交互OfficeServer的OPTION="DATETIME"   true表示返回并设置本地时间为服务器时间；false表示仅返回服务器时间
  alert("提示：已经设置本地时间为 "+mResult);    //该功能主要用于在痕迹保留时读取服务器时间
}


//作用：表格生成及填充
function WebSetWordTable(){
  var mText="",mName="",iColumns,iCells,iTable;
  //设置COMMAND为WORDTABLE
  webform.WebOffice.WebSetMsgByName("COMMAND","WORDTABLE");   //设置变量COMMAND="WORDTABLE"，在WebSendMessage()时，一起提交到OfficeServer中
  //发送到服务器上
  //如果没有错误
  if (webform.WebOffice.WebSendMessage()){                //交互OfficeServer的OPTION="SENDMESSAGE"
	iColumns = webform.WebOffice.WebGetMsgByName("COLUMNS");  //取得列
	iCells = webform.WebOffice.WebGetMsgByName("CELLS");      //取得行
	iTable=webform.WebOffice.WebObject.Tables.Add(webform.WebOffice.WebObject.Application.Selection.Range,iCells,iColumns);   //生成表格
	for (var i=1; i<=iColumns; i++)
	{
   	  for (var j=1; j<=iCells; j++)
	  {
		mName=i.toString()+j.toString();
		mText=webform.WebOffice.WebGetMsgByName(mName);	 //取得OfficeServer中的表格内容
		iTable.Columns(i).Cells(j).Range.Text=mText;   	//填充单元值
	   }
	}
   }
   StatusMsg(webform.WebOffice.Status);
}


//作用：获取文档Txt正文
function WebGetWordContent(){
  try{
    alert(webform.WebOffice.WebObject.Content.Text);
  }catch(e){}
}

//作用：写Word内容
function WebSetWordContent(){
  var mText=window.prompt("请输入内容:","测试内容");
  if (mText==null){
     return (false);
  }
  else
  {
     //下面为显示选中的文本
     //alert(webform.WebOffice.WebObject.Application.Selection.Range.Text);
     //下面为在当前光标出插入文本
     webform.WebOffice.WebObject.Application.Selection.Range.Text= mText+"\n";
     //下面为在第一段后插入文本
     //webform.WebOffice.WebObject.Application.ActiveDocument.Range(1).Text=(mText);
  }
}


//作用：打印黑白文档
function WebWordPrintBlackAndWhile(){
   var i,n;

   //图片变黑白
   i=0;
   n=webform.WebOffice.WebObject.Shapes.Count;
   for (var i=1; i<=n; i++)
   {
      webform.WebOffice.WebObject.Shapes.Item(i).PictureFormat.ColorType=3;
   }
   i=0;
   n=webform.WebOffice.WebObject.InlineShapes.Count;
   for (var i=1; i<=n; i++)
   {
      webform.WebOffice.WebObject.InlineShapes.Item(i).PictureFormat.ColorType=3;
   }

   //文字变黑白
   webform.WebOffice.WebObject.Application.Selection.WholeStory();
   webform.WebOffice.WebObject.Application.Selection.Range.Font.Color = 0;
}

function WebWordPrintEscTitle(){
   var i,n;
   //图片变黑白
  /*  i=0;
   n=webform.WebOffice.WebObject.Shapes.Count;
   for (var i=1; i<=n; i++)
   {
      webform.WebOffice.WebObject.Shapes.Item(i).PictureFormat.ColorType=3;
   }*/
  //xiantiao
   i=0;
   //alert(webform.WebOffice.WebObject.InlineShapes.Count);
   n=webform.WebOffice.WebObject.InlineShapes.Count;  
  
   for (var i=1; i<=n; i++)
   {
	  //webform.WebOffice.WebObject.InlineShapes.Item(i).PictureFormat.ColorType=3;
	  webform.WebOffice.WebObject.InlineShapes.Item(i).ConvertToShape();//先转成Shape才能进行下面的隐藏操作	  
	  //webform.WebOffice.WebObject.InlineShapes.Item(i).Width=10;
   }
   //文字变黑白
   //webform.WebOffice.WebObject.Application.Selection.WholeStory();//选择整个文档
   //webform.WebOffice.WebObject.Application.ActiveDocument.Bookmarks("Title").Select();
   //alert(webform.WebOffice.WebObject.Application.Selection.ShapeRange.Count);
   //webform.WebOffice.WebObject.Application.Selection.Range.Font.Color = 16777215;
   var count=webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Count;
   //alert("count:"+count);
   
   try{	 
	   for(var j=1;j<=count;j++)
	   {
		   var type=webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(j).Type; 	//13图片，9线
		   
		   if(type==17)//17表示文本框
		   {
			   continue;
		   }	    	 
		   webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(j).Visible=0;

	   }

   }catch(e){
	   alert(e);
   }
     
   
   //var count=webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Count;  
   
   //for(var i=1;i<=count;i++)
   //{
     
   /*  alert("3:"+webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(3).Type);
     webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(2).Visible=0;
     alert("2:"+webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(2).Type);
   //}   
   webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(1).Select();   //1表示文档中最开始的第一根线  
   webform.WebOffice.WebObject.Application.Selection.ShapeRange.Line.ForeColor.RGB = 16777215;    //同时使线变成白色
   
   
   webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(3).Select();   //1表示文档中最开始的第一根线  
   webform.WebOffice.WebObject.Application.Selection.ShapeRange.Line.ForeColor.RGB = 16777215;    //同时使线变成白色
   webform.WebOffice.WebObject.Application.Selection.ShapeRange.Fill.ForeColor.RGB = 16777215; 
   */
   webform.WebOffice.WebOpenPrint();
    
   for(var i=1;i<=count;i++)
   {
	 if(webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(i).Visible==0)
	 {
		 webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(i).Visible=1; 
	 }
   }
   //webform.WebOffice.WebObject.Application.ActiveDocument.Bookmarks("Title").Select();
   //webform.WebOffice.WebObject.Application.Selection.Range.Font.Color = 255;
   //for(var i=1;i<=count;i++)
   //{
   /*
     webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(3).Visible=1;//显示红头
      
   webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(1).Select();   //1表示文档中最开始的第一根线
   webform.WebOffice.WebObject.Application.Selection.ShapeRange.Line.ForeColor.RGB = 255;    //同时使线变成白色
   webform.WebOffice.WebObject.Application.ActiveDocument.Shapes.Item(3).Select();   //1表示文档中最开始的第一根线
   webform.WebOffice.WebObject.Application.Selection.ShapeRange.Line.ForeColor.RGB = 255;    //同时使线变成白色
   webform.WebOffice.WebObject.Application.Selection.ShapeRange.Fill.ForeColor.RGB = 255;
  */
}
//作用：VBA套红
function WebInsertVBA(){

	//画线
	var object=webform.WebOffice.WebObject;
	var myl=object.Shapes.AddLine(100,60,305,60)
	myl.Line.ForeColor=255;
	myl.Line.Weight=2;
	var myl1=object.Shapes.AddLine(326,60,520,60)
	myl1.Line.ForeColor=255;
	myl1.Line.Weight=2;

	//object.Shapes.AddLine(200,200,450,200).Line.ForeColor=6;
   	var myRange=webform.WebOffice.WebObject.Range(0,0);
	myRange.Select();

	var mtext="★";
	webform.WebOffice.WebObject.Application.Selection.Range.InsertAfter (mtext+"\n");
   	var myRange=webform.WebOffice.WebObject.Paragraphs(1).Range;
   	myRange.ParagraphFormat.LineSpacingRule =1.5;
   	myRange.font.ColorIndex=6;
   	myRange.ParagraphFormat.Alignment=1;
   	myRange=webform.WebOffice.WebObject.Range(0,0);
	myRange.Select();
	mtext="金格发[２００３]１５４号";
	webform.WebOffice.WebObject.Application.Selection.Range.InsertAfter (mtext+"\n");
	myRange=webform.WebOffice.WebObject.Paragraphs(1).Range;
	myRange.ParagraphFormat.LineSpacingRule =1.5;
	myRange.ParagraphFormat.Alignment=1;
	myRange.font.ColorIndex=1;

	mtext="金格电子政务文件";
	webform.WebOffice.WebObject.Application.Selection.Range.InsertAfter (mtext+"\n");
	myRange=webform.WebOffice.WebObject.Paragraphs(1).Range;
	myRange.ParagraphFormat.LineSpacingRule =1.5;

	//myRange.Select();
	myRange.Font.ColorIndex=6;
	myRange.Font.Name="仿宋_GB2312";
	myRange.font.Bold=true;
	myRange.Font.Size=50;
	myRange.ParagraphFormat.Alignment=1;

	//myRange=myRange=webform.WebOffice.WebObject.Paragraphs(1).Range;
	webform.WebOffice.WebObject.PageSetup.LeftMargin=70;
	webform.WebOffice.WebObject.PageSetup.RightMargin=70;
	webform.WebOffice.WebObject.PageSetup.TopMargin=70;
	webform.WebOffice.WebObject.PageSetup.BottomMargin=70;
}


//作用：保存定稿文件
function WebUpdateFile(){
  if (webform.WebOffice.WebUpdateFile()){                //交互OfficeServer的OPTION="UPDATEFILE"，类似WebSave()或WebSaveVersion()方法
     StatusMsg(webform.WebOffice.Status);
  }else{
     StatusMsg(webform.WebOffice.Status);
  }
}



//打印份数控制
function WebCopysCtrlPrint(){
  var mCopies,objPrint;
  objPrint = webform.WebOffice.WebObject.Application.Dialogs(88);     //打印设置对话框
  if (objPrint.Display==-1){
    mCopies=objPrint.NumCopies;    //取得需要打印份数
    webform.WebOffice.WebSetMsgByName("COMMAND","COPIES");
    webform.WebOffice.WebSetMsgByName("OFFICEPRINTS",mCopies.toString());   //设置变量OFFICEPRINTS的值，在WebSendMessage()时，一起提交到OfficeServer中
    webform.WebOffice.WebSendMessage();                               //交互OfficeServer的OPTION="SENDMESSAGE"       
    if (webform.WebOffice.Status=="1") {
      alert("可以允许打印，注：该实例设置总文档打印份数2份");
      objPrint.Execute;
    }else{
      alert("已超出允许的打印份数");
      return false;
    }
  }
}


//作用：导入Text
function WebInportText(){
    var mText;
    webform.WebOffice.WebSetMsgByName("COMMAND","INPORTTEXT");  //设置变量COMMAND="INPORTTEXT"，在WebSendMessage()时，一起提交到OfficeServer中
    if (webform.WebOffice.WebSendMessage()){                    //交互OfficeServer的OPTION="SENDMESSAGE"
      mText=webform.WebOffice.WebGetMsgByName("CONTENT");       //取得OfficeServer传递的变量CONTENT值
      webform.WebOffice.WebObject.Application.Selection.Range.Text=mText;
      alert("导入文本成功");
    }
    StatusMsg(webform.WebOffice.Status);
}


//作用：导出Text
function WebExportText(){
    var mText=webform.WebOffice.WebObject.Content.Text;
    webform.WebOffice.WebSetMsgByName("COMMAND","EXPORTTEXT");  //设置变量COMMAND="EXPORTTEXT"，在WebSendMessage()时，一起提交到OfficeServer中
    webform.WebOffice.WebSetMsgByName("CONTENT",mText);         //设置变量CONTENT="mText"，在WebSendMessage()时，一起提交到OfficeServer中，可用于实现全文检索功能，对WORD的TEXT内容进行检索
    if (webform.WebOffice.WebSendMessage()){                    //交互OfficeServer的OPTION="SENDMESSAGE"
      alert("导出文本成功");
    }
    StatusMsg(webform.WebOffice.Status);
}


//作用：获取文档页数
function WebDocumentPageCount(){
    if (webform.WebOffice.FileType==".doc"){
	var intPageTotal = webform.WebOffice.WebObject.Application.ActiveDocument.BuiltInDocumentProperties(14);
	alert("文档页总数："+intPageTotal);
    }
    if (webform.WebOffice.FileType==".wps"){
	var intPageTotal = webform.WebOffice.WebObject.PagesCount();
	alert("文档页总数："+intPageTotal);
    }
}

//作用：签章锁定文件功能
function WebSignatureAtReadonly(){
  webform.WebOffice.WebSetProtect(false,"");                  //解除文档保护
  webform.WebOffice.WebSetRevision(false,false,false,false);  //设置文档痕迹保留的状态  参数1:不显示痕迹  参数2:不保留痕迹  参数3:不打印时有痕迹  参数4:不显痕迹处理工具
  try{
    webform.WebOffice.WebOpenSignature();                     //交互OfficeServer的 A签章列表OPTION="LOADMARKLIST"    B签章调出OPTION="LOADMARKIMAGE"    C确定签章OPTION="SAVESIGNATURE"    <参考技术文档>    文档中要定义标签Manager，可以自行修改标签名称
    StatusMsg(webform.WebOffice.Status);
  }catch(e){}
  webform.WebOffice.WebSetProtect(true,"");                   //锁定文档
}
