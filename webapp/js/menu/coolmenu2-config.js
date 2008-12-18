/***
This is the menu creation code - place it right after you body tag
Feel free to add this to a stand-alone js file and link it to your page.
**/

//Menu object creation
oCMenu=new makeCM("oCMenu") //Making the menu object. Argument: menuname

oCMenu.frames = 0

//Menu properties
oCMenu.pxBetween=0
oCMenu.fromLeft=-1
oCMenu.fromTop=41
oCMenu.rows=1
oCMenu.menuPlacement="left"
oCMenu.offlineRoot="http://www.bcb.gov.br"
oCMenu.onlineRoot=""
oCMenu.wait=1000
oCMenu.zIndex=0
oCMenu.resizeCheck=1
oCMenu.checkselect=1
oCMenu.useNS4links=1
oCMenu.NS4padding=2


//Background bar properties
oCMenu.useBar=1
/*
alterado por DEINF.SDANTAS para resolver o problema de barras de 
rolagem horizontais que estava acontecendo no firefox ao reduzir para 
resolu��o 800x600

c�digo original:
oCMenu.barWidth = "100%"
*/
oCMenu.barWidth = window.navigator.appName == "Netscape" ? "98%" : "100%"
oCMenu.barHeight=11
oCMenu.barClass=0
oCMenu.barX=0
oCMenu.barY=0
oCMenu.barBorderX=0
oCMenu.barBorderY=3
oCMenu.barBorderClass="clBar"

//Level properties - ALL properties have to be spesified in level 0
oCMenu.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu.level[0].width=100
oCMenu.level[0].height=18
oCMenu.level[0].borderX=1
oCMenu.level[0].borderY=-2
oCMenu.level[0].offsetX=47
oCMenu.level[0].offsetY=5
oCMenu.level[0].align="bottom"
oCMenu.level[0].arrow=0
oCMenu.level[0].arrowWidth=10
oCMenu.level[0].arrowHeight=10
oCMenu.level[0].regClass="cmMenu"
oCMenu.level[0].overClass="cmMenuOver"
oCMenu.level[0].borderClass="cmMenuBorder"
oCMenu.level[0].roundBorder=1


//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to specify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[1]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu.level[1].width=150
oCMenu.level[1].height=16
oCMenu.level[1].borderX=0
oCMenu.level[1].borderY=0
oCMenu.level[1].align="right"
oCMenu.level[1].offsetX=-12
oCMenu.level[1].offsetY=-1
oCMenu.level[1].arrow="imagens/seta.gif"
oCMenu.level[1].regClass="cmItem"
oCMenu.level[1].overClass="cmItemOver"
oCMenu.level[1].borderClass="cmItemBorder"

/******************************************
CM_ADD-IN - hideselectboxes (last updated: 11/13/02)
IE5+ and NS6+ only - ignores the other browsers

Because of the selectbox bug in the browsers that makes
selectboxes have the highest z-index whatever you do
this script will check for selectboxes that interfear with
your menu items and then hide them.

Just add this code to the coolmenus js file
or link the cm_addins.js file to your page as well.
*****************************************/
if(bw.dom&&!bw.op){
  makeCM.prototype.sel=0
  makeCM.prototype.onshow+=";this.hideselectboxes(pm,pm.subx,pm.suby,maxw,maxh,pm.lev)"
  makeCM.prototype.hideselectboxes=function(pm,x,y,w,h,l){
    var selx,sely,selw,selh,i
    if(!this.sel){
      this.sel=this.doc.getElementsByTagName("SELECT")
		  this.sel.level=0
    }
    var sel=this.sel
    for(i=0;i<sel.length;i++){
			selx=0; sely=0; var selp;
			if(sel[i].offsetParent){selp=sel[i]; while(selp.offsetParent){selp=selp.offsetParent; selx+=selp.offsetLeft; sely+=selp.offsetTop;}}
			selx+=sel[i].offsetLeft; sely+=sel[i].offsetTop
			selw=sel[i].offsetWidth; selh=sel[i].offsetHeight
			if(selx+selw>x && selx<x+w && sely+selh>y && sely<y+h){
				if(sel[i].style.visibility!="hidden"){sel[i].level=l; sel[i].style.visibility="hidden"; if(pm){ if(!pm.mout) pm.mout=""; pm.mout+=this.name+".sel["+i+"].style.visibility='visible';"}}
      }else if(l<=sel[i].level && !(pm&&l==0)) sel[i].style.visibility="visible"
    }
  }
}

/******************
CM_ADD-IN - filterIt (last updated: 01/26/02)

Explorer5.5+ only. Other browser will ignore it.

This function uses filters for Explorer to show
the subitems.
If you use this add-in you will get 1 new
level property called "filter". You have
to specify which filter to use and what
level to use them on.
(this properties will also be inherited though)

Example setting:
oCMenu.level[3].filter="progid:DXImageTransform.Microsoft.Fade(duration=0.5)"

Examples on how to use this will come later.

Just add this code to the coolmenus js file
or link the cm_addins.js file to your page as well.
*****************/
bw.filter=(bw.ie55||bw.ie6) && !bw.mac
makeCM.prototype.onshow+=";if(c.l[pm.lev].filter) b.filterIt(c.l[pm.lev].filter)"
cm_makeLevel.prototype.filter=null
cm_makeObj.prototype.filterIt=function(f){
  if(bw.filter){
    if(this.evnt.filters[0]) this.evnt.filters[0].Stop();
    else this.css.filter=f;
    this.evnt.filters[0].Apply();
    this.showIt();
    this.evnt.filters[0].Play();
  }
}
/******************
CM_ADD-IN - slide (last updated: 01/26/02)

This works in all browsers, but it can be
unstable on all other browsers then Explorer.

This function shows the submenus in a sliding
effect. If you use this add-in you get two
new level properties called "slidepx" and
"slidetim". You have to specify this for
the levels you want this to happen on
(these properties will also be inherited though)

slidepx is the number of pixels you want the
div to slide each setTimout, while "slidetim"
is the setTimeout speed (in milliseconds)

Example setting:
oCMenu.level[3].slidepx=10
oCMenu.level[3].slidetim=20

Just add this code to the coolmenus js file
or link the cm_addins.js file to your page as well.
*****************/
makeCM.prototype.onshow+="; if(c.l[pm.lev].slidepx){b.moveIt(x,b.y-b.h); b.showIt(); b.tim=null; b.slide(y,c.l[pm.lev].slidepx,c.l[pm.lev].slidetim,c,pm.lev,pm.name)}"
makeCM.prototype.going=0
cm_makeObj.prototype.tim=10;
cm_makeLevel.prototype.slidepx=null
cm_makeLevel.prototype.slidetim=30
cm_makeObj.prototype.slide=function(end,px,tim,c,l,name){
  if(!this.vis || c.l[l].a!=name) return
	if(this.y<end-px){
		if(this.y>(end-px*px-px) && px>1) px-=px/5; this.moveIt(this.x,this.y+px)
		this.clipTo(end-this.y,this.w,this.h,0)
		this.tim=setTimeout(this.obj+".slide("+end+","+px+","+tim+","+c.name+","+l+",'"+name+"')",tim)
	}else{this.moveIt(this.x,end)}
}
/******************
CM_ADD-IN - clipout (last updated: 01/26/02)

This works in all browsers, but it can be
unstable on all other browsers then Explorer.

This function shows the submenus with a clipping
effect. If you use this add-in you get two
new level properties called "clippx" and
"cliptim". You have to specify this for
the levels you want this to happen on
(these properties will also be inherited though)

"clippx" is the number of pixels you want the
div to slide each setTimout, while "cliptim"
is the setTimeout speed (in milliseconds)

Example setting:
oCMenu.level[3].clippx=10
oCMenu.level[3].cliptim=20

Just add this code to the coolmenus js file
or link the cm_addins.js file to your page as well.

*****************/
makeCM.prototype.onshow+="if(c.l[pm.lev].clippx){h=b.h; if(!rows) b.clipTo(0,maxw,0,0,1); else b.clipTo(0,0,maxh,0,1); b.clipxy=0; b.showIt(); clearTimeout(b.tim); b.clipout(c.l[pm.lev].clippx,!rows?maxw:maxh,!rows?maxh:maxw,c.l[pm.lev].cliptim,rows)}"
cm_makeObj.prototype.tim=10;
cm_makeLevel.prototype.clippx=null
cm_makeLevel.prototype.cliptim=30
cm_makeObj.prototype.clipxy=0
cm_makeObj.prototype.clipout=function(px,w,stop,tim,rows){
	if(!this.vis) return; if(this.clipxy<stop-px){this.clipxy+=px;
  if(!rows) this.clipTo(0,w,this.clipxy,0,1);
  else this.clipTo(0,this.clipxy,w,0,1);
  this.tim=setTimeout(this.obj+".clipout("+px+","+w+","+stop+","+tim+","+rows+")",tim)
	}else{if(bw.ns6){this.hideIt();}; if(!rows) this.clipTo(0,w,stop,0,1); else this.clipTo(0,stop,w,0,1);if(bw.ns6){this.showIt()}}
}

