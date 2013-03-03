function getXmlHttp()
{
 	var xmlHttp;
 
 	try
    {
   		// Firefox, Opera 8.0+, Safari
    	xmlHttp=new XMLHttpRequest();
    }
 	catch (e)
    {
  		// Internet Explorer
   		try
     	{
      		xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
      	}
   		catch (e)
  		{
      		try
         	{
         		xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
         	}
      		catch (e)
         	{
         		alert("����������֧��AJAX��");
         		return false;
         	}
      	}
	}
		
	return xmlHttp;
}

function submitForm(formName, actionURL)
{
	try
	{
		var form = document.getElementById(formName);
		form.action = actionURL;
		form.submit();
	}
	catch(e)
	{
		return;
	}
}


function submitFormInNewWindow(formName, actionURL)
{
	try
	{
		var form = document.getElementById(formName);
		form.action = actionURL;
		form.target = "_blank";
		form.submit();
	}
	catch(e)
	{
		return;
	}
}


function submitFormWithConfirm(formName, actionURL, confirmMessage)
{
	try
	{
		if (!confirm(confirmMessage)) return;
		
		submitForm(formName, actionURL);
	}
	catch(e)
	{
		return;
	}
}


function toggleAll(formName, itemName)
{
  try
	{
	    var form = document.forms[formName];
	    
	    if (form.elements['chkAllIDs'].checked)
	        selectedAllCheckbox(formName, itemName); 
	    else  
	        unSelectedAllCheckbox(formName, itemName); 
	}
	catch(e)
	{
	    return;
	}
}

function selectedAllCheckbox(formName, chkName)
{
	try
	{
		var form = document.forms[formName];
		var item = form.elements[chkName];	
		if (item == null) return;
		
		if (item.length) 
		{
            for (var i=0;i<item.length;i++)
            {
                item[i].checked = true;   
        	}
        }
        else
        {
            item.checked = true;
        }
	}
	catch(e)
	{
		return;
	}
}

function unSelectedAllCheckbox(formName, chkName)
{
	try
	{
		var form = document.forms[formName];
		var item = form.elements[chkName];	
		if (item == null) return;
		
		if (item.length) 
		{
            for (var i=0;i<item.length;i++)
            {
                item[i].checked = false;   
        	}
        }
        else
        {
            item.checked = false;
        }
	}
	catch(e)
	{
		return;
	}
}


function checkIds(form, itemName)
{
	try
	{
		var item = form.elements[itemName];
		
		if (item == null) return false;
		
		if (item.length) 
		{
            for (var i=0;i<item.length;i++)
            {
                if (item[i].checked) return true;    
        	}
        }
        else
        {
            if (item.checked) return true;
        }
        
        return false;
	}
	catch(e)
	{
		return false;
	}	
}

function getValueOfFirstSelectedCheckBox(form, chkName)
{
    try
    {
        var item = form.elements[chkName];
        
        if (item == null) return "";
        
        if (item.length)
        {
            for (var i=0;i<item.length;i++)
            {
                if (item[i].checked) return item[i].value;
            }
        }
        else
        {
            if (item.checked) return item.value;
        }
        
        return "";
    }
    catch(e)
    {
        return "";
    }
}

function isOnlyOneSelected(form, itemName)
{
	try
	{
		var item = form.elements[itemName];
		
		if (item == null) return false;
		
		if (item.length) 
		{
			var _cnt = 0;
      		for (var i=0;i<item.length;i++)
      		{
        		if (item[i].checked) _cnt = _cnt +1;   
      		}
        	
      		if (_cnt == 1) return true
    	}
    	else
    	{
      		if (item.checked) return true;
    	}
        
    	return false;
	}
	catch(e)
	{
		return false;
	}
}























function changeToURL(url_)
{
    window.location.href = url_;
}   
function doAction(formName, actionValue)
{
	try
	{
		var form = document.forms[formName];
		
		if (!trimForm(form)) return ;
		
		form.elements["userAction"].value = actionValue;
		form.target = "_self";
		form.submit();
	}
	catch(e)
	{
		return;
	}
}


function submitWithConfirm(actionURL, confirmMessage)
{
	try
	{
		if (!confirm(confirmMessage)) return;
		
		changeToURL(actionURL);
	}
	catch(e)
	{
		return;
	}
}



/**
 * open popup window
 */
function openWindow(url, winName, features) 
{
	if(features==""){
		features="width=600,height=400,scrollbars=1";
   	}
   	window.open(url, winName, features);
}

function closeWindow()
{
	window.close();
}










/**
* validate the checkbox is selected .
* szFormName:HTML form,szCheckBoxName:HTML checkbox
*/
function isCheckboxSelected(szFormName, szCheckBoxName) 
{
   return getSelectedCheckbox(szFormName, szCheckBoxName).length > 0;
}

/**
* get the Checkbox.
* szFormName:HTML form,szCheckBoxName:HTML checkbox
*/
function getSelectedCheckbox(szFormName, szCheckBoxName) 
{
    var buttonGroup = eval("document." + szFormName + "." + szCheckBoxName); 
    var retArr = new Array();
    var lastElement = 0;

    if (!buttonGroup)
        return retArr;  
    
    // if the button group is an array (one check box is not an array)
    if (buttonGroup[0]) 
    { 
		for (var i=0; i<buttonGroup.length; i++)
        {
            if (buttonGroup[i].checked) 
            {
                retArr.length = lastElement;
                retArr[lastElement] = i;
                lastElement++;
            }
        }
    }
    else 
    { // There is only one check box (it's not an array)
        if (buttonGroup.checked) 
        { // if the one check box is checked
            retArr.length = lastElement;
            retArr[lastElement] = 0; // return zero as the only array value
        }
    }

    return retArr;
}


/**
* get the Checkbox's value.
* szFormName:HTML form,szCheckBoxName:HTML checkbox
*/
function getSelectedCheckboxValue(szFormName, szCheckBoxName) 
{
    buttonGroup = eval("document." + szFormName + "." + szCheckBoxName);
    var retArr = new Array();
    var lastElement = 0;
    
    if (!buttonGroup)
        return retArr;
    
    // if the button group is an array (one check box is not an array)
    if (buttonGroup[0]) 
    { 
        for (var i=0; i<buttonGroup.length; i++) 
        {
            if (buttonGroup[i].checked) 
            {
                retArr.length = lastElement;
                retArr[lastElement] = buttonGroup[i].value;
                lastElement++;
            }
        }
    }
    else 
    { // There is only one check box (it's not an array)
        if (buttonGroup.checked) 
        { // if the one check box is checked
            retArr.length = lastElement;
            retArr[lastElement] = buttonGroup.value; // return that value
        }
    }

    return retArr;
}


function trimValue(value)
{
    if (!value){
        return "";
    }
        
    if (typeof value != "string") { 
    	return value; 
    }
        
    var start = 0;
    var end = value.length;

    while (start < end && value.substring(start, start+1) == " ")
    {
        start++;
    }
    
    while (start < end && value.substring(end-1, end) == " ")
    {
        end--;
    }
    
    return value.substring(start, end);	
}

/**
 * trim string
 */
function trim(formName, itemName)
{
    var obj = eval("document."+formName+"." + itemName);
    
    if(!obj){
        return "";
    }
        
    return trimValue(obj.value);
}

function trimForm(_form)
{ 
	if (typeof(_form) != "object") return false;

    for (var i=0; i<_form.elements.length; i++) 
	{
		var el = _form.elements[i];
		if (el.type == 'text') 
		{
			el.value = trimValue(el.value);
		}
    }
    
	return true;
}


/**
 * check whether empty
 */
function isEmpty(formName, itemName)
{
    var obj = eval("document."+formName+"." + itemName);
    
    if(!obj){
        return true;
    }

    return isValueEmpty(obj.value);
}

/**
 * check whether empty
 */
 
function isValueEmpty(vlaue)
{
     var obj = trimValue(vlaue);
     
     if(!obj || obj == ""){
        return true;
     }
     return false;
}

/**
 * check whether float
 */
function isFloat(num)
{
 	var i,j,strTemp;
 	strTemp="0123456789.";
	if(num.lastIndexOf('.')!=num.indexOf('.')){
		return false;
	}
	if(num.charAt(num.length)=="."){
	  	return false;
	}
	if ( num.length== 0){
	 	 return false;
	}
	for (i=0;i<num.length;i++)
	{
		j=strTemp.indexOf(num.charAt(i)); 
		if (j==-1)
	 	{
			return false;
	 	}
	 }
 	return true;
}

/**
 * check whether int
 */
function isInteger(num)
{
 	var i,j,strTemp;
 	strTemp="0123456789";
	if ( num.length== 0)
	 	 return false;
	for (i=0;i<num.length;i++)
	 {
		j=strTemp.indexOf(num.charAt(i)); 
		if (j==-1)
	 	{
			return false;
	 	}
	 }
 	return true;
}

function findPosition(object)
{
		var curleft = curtop = 0;
		if (object.offsetParent) 
		{
    		curleft = object.offsetLeft
    		curtop = object.offsetTop;
		    while (object = object.offsetParent) 
		    {
    			curleft += object.offsetLeft
    			curtop += object.offsetTop
		    }
        }
        
	    return [curleft,curtop];
}

function setStatementDetails(object,layer)
{
		var coors = findPosition(object);
		var sdlayer = document.getElementById(layer);
		sdlayer.style.top = coors[1] + 26 + 'px';
		sdlayer.style.left = coors[0] - 200 + 'px';
		sdlayer.style.display = "block";
}
	
function unsetStatementDetails(object,layer)
{
		var sdlayer = document.getElementById(layer);
		sdlayer.style.display = "none";
}

function removeSelectOptions(selObjName)
{
    var objSel = document.getElementById(selObjName);
    
	try
	{
		if(objSel.options.length > 0)
		{
			var len = objSel.options.length;
			for (var i=0; i< len; i++) objSel.remove(0); 
		}
			
		objSel.value = "";
	}
	catch(e)
	{}
}

function addOneOptionIntoSelection(selObjName, value, text, isSelected)
{
	var opt = document.createElement('option');
	opt.value = value;
	opt.innerHTML = text;
	if (isSelected == "true") opt.selected = true;
	
	document.getElementById(selObjName).appendChild(opt);
}

function clearObjectValue(objName)
{
	try
	{
		var obj = document.getElementById(objName);
		obj.value = "";
	}
	catch(e)
	{
	}
}