package com.selene.common;

import com.selene.common.util.DataUtil;

public class DataUtilTest {
	public static void main(String[] args) {
		String a="<div class=\"clearfix w1000_320 text_title\">"+
	"<h3 class=\"pre\"></h3>"+
	"<h1>所罗门群岛决定同中国建交&nbsp;外交部:赞赏&nbsp;支持</h1>"+
	"<h4 class=\"sub\"></h4>"+
    "<p class=\"author\"></p>"+
   "<div class=\"box01\">"+
      "<div class=\"fl\">2019年09月17日21:30&nbsp;&nbsp;来源：<a href=\"http://world.people.com.cn/\" target=\"_blank\">人民网-国际频道</a></div>"+
      "<div class=\"fr\">"+
         "<div class=\"fx\">"+
        " <div id=\"ops_share\"><div class=\"ops_shareLayer\"><span class=\"ops_tit\">分享到：</span><ul class=\"ops_icons\"><li><a href=\"javascript:void(0)\" onclick=\"_opsShare.share('icon_rmwb');return false;\" class=\"icon_rmwb\" title=\"人民微博\"><i> </i></a></li><li><a href=\"javascript:void(0)\" onclick=\"_opsShare.share('icon_sina');return false;\" class=\"icon_sina\" title=\"新浪微博\"><i> </i></a></li><li><a href=\"javascript:void(0)\" onclick=\"_opsShare.share('icon_weixin');return false;\" class=\"icon_weixin\" title=\"微信\"><i> </i></a></li><li><a href=\"javascript:void(0)\" onclick=\"_opsShare.share('icon_qzone');return false;\" class=\"icon_qzone\" title=\"QQ空间\"><i> </i></a></li><li><a href=\"javascript:void(0)\" onclick=\"_opsShare.share('icon_copy');return false;\" class=\"icon_copy\" title=\"复制地址\"><i> </i></a></li></ul></div></div>"+
		 "<script src=\"http://www.people.com.cn/img/2016wb/share_qr.min.js\" charset=\"utf-8\"></script>"+
         "</div>"+
         "<div class=\"message\" id=\"rwb_bbstop\"><a href=\"http://bbs1.people.com.cn/postLink.do?nid=31358435\" target=\"_blank\"><img src=\"http://www.people.com.cn/img/2016wb/images/icon04.jpg\" width=\"29\" height=\"23\"></a>&nbsp;</div>"+
      "</div>"+
  " </div>"+
"</div>";
		
		System.out.println(DataUtil.unescape(a));
		System.out.println(DataUtil.clear(a));
		System.out.println(DataUtil.filter(a));
	}
}
