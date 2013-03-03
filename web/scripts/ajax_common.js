//this file must be loaded after prototype.js

if (!document.all)
{
    var  ex;
    XMLDocument.prototype.__proto__.__defineGetter__( "xml" ,  function (){
         try {
             return   new  XMLSerializer().serializeToString( this );
        } catch (ex){
             var  d  =  document.createElement( "div" );
            d.appendChild( this .cloneNode( true ));
             return  d.innerHTML;
        }
    });
    Element.prototype.__proto__.__defineGetter__( "xml" ,  function (){
         try {
             return   new  XMLSerializer().serializeToString( this );
        } catch (ex){
             var  d  =  document.createElement( "div" );
            d.appendChild( this .cloneNode( true ));
             return  d.innerHTML;
        }
    });
    XMLDocument.prototype.__proto__.__defineGetter__( "text" ,  function (){
         return   this .firstChild.textContent
    });
    Element.prototype.__proto__.__defineGetter__( "text" ,  function (){
         return   this .textContent
    });

    XMLDocument.prototype.selectSingleNode = Element.prototype.selectSingleNode = function (xpath){
         var  x = this .selectNodes(xpath)
         if ( ! x  ||  x.length < 1 ) return   null ;
         return  x[ 0 ];
    }
    XMLDocument.prototype.selectNodes = Element.prototype.selectNodes = function (xpath){
         var  xpe  =   new  XPathEvaluator();
         var  nsResolver  =  xpe.createNSResolver( this .ownerDocument  ==   null   ?
             this .documentElement :  this .ownerDocument.documentElement);
         var  result  =  xpe.evaluate(xpath,  this , nsResolver,  0 ,  null );
         var  found  =  [];
         var  res;
         while  (res  =  result.iterateNext())
            found.push(res);
         return  found;
    }
}

function obtainXmlDomObj(str)
{
	var xmlObj;
	if (document.all) 
    {
    	xmlObj = new ActiveXObject("Microsoft.XMLDOM");
        xmlObj.loadXML(str);
    }
    else
    {
    	var  parser = new  DOMParser();
        xmlObj = parser.parseFromString(str,"text/xml");
    }
    
    return xmlObj;
}

function obtainValueFromSingleNode(xmlObj_, key_)
{
    try
    {
		var obj_ =  xmlObj_.selectSingleNode(key_);
	
		return obj_.firstChild.nodeValue;	
	}
	catch(e)
	{
		return 
	}
}