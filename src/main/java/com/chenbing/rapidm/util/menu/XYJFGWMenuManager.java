package com.chenbing.rapidm.util.menu;


import com.alibaba.fastjson.JSONObject;
import com.chenbing.rapidm.config.HttpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XYJFGWMenuManager {

    private Log logger= LogFactory.getLog(this.getClass());

    @Autowired
    private HttpUtil httpUtil;

	public static void main( String[] args ) {

	}

	public void createMenu(String token,String domain,String appid ){

        String url =  "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
        Menu menu = getProductMenuFor3_8(domain,appid);
        httpUtil.postForObject(url,JSONObject.parseObject(JSONObject.toJSONString(menu)));

    }

	/**
	 *
	 * 3.8版本菜单
	 *
	 * @return
	 */
	public static Menu getProductMenuFor3_8(String domain ,String appid) {

		// 第一按钮
		ViewButton btn11 = new ViewButton();
		btn11.setName( "在线顾问" );
        btn11.setType( "view" );
        btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3A%2F%2F"+domain+"%2Fwebsocket%2Findex.html&response_type=code&scope=snsapi_userinfo&state=10004#wechat_redirect");


		// 第二按钮
		ViewButton btn21 = new ViewButton();
		btn21.setName( "找产品" );
		btn21.setType( "click" );
		btn21.setKey( "key_get_message3" );

		ViewButton btn22 = new ViewButton();
		btn22.setName( "✨定制贷款方案" );
		btn22.setType( "view" );
		btn22.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3a%2f%2f"+domain+"%2fxyguwen%2findex.html%3fsource%3dmenu%23%2fcustomize&response_type=code&scope=snsapi_userinfo&state=25073#wechat_redirect" );

		ViewButton btn23 = new ViewButton();
		btn23.setName( "评额度" );
		btn23.setType( "click" );
		btn23.setKey( "key_get_message2" );

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName( "我要" );
		mainBtn2.setSub_button( new Button[] { btn21 , btn22 , btn23 } );

		// 第三按钮
		ViewButton btn31 = new ViewButton();
		btn31.setName( "还款提醒" );
		btn31.setType( "view" );
		btn31.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3a%2f%2f"+domain+"%2fxyguwen%2findex.html%23%2fremind%3fsource%3dmenu&response_type=code&scope=snsapi_userinfo&state=10006#wechat_redirect " );

		ViewButton btn32 = new ViewButton();
		btn32.setName( "个人中心" );
		btn32.setType( "view" );
		btn32.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3a%2f%2f"+domain+"%2fxyguwen%2findex.html%23%2fpersonal%3fsource%3dmenu&response_type=code&scope=snsapi_userinfo&state=10005#wechat_redirect" );

		ViewButton btn33 = new ViewButton();
		btn33.setName( "贷款攻略" );
		btn33.setType( "view" );
		btn33.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3a%2f%2f"+domain+"%2fxyguwen%2findex.html%23%2flc%3fsource%3dmenu&response_type=code&scope=snsapi_userinfo&state=10002#wechat_redirect" );

		ViewButton btn34 = new ViewButton();
		btn34.setName( "投诉与反馈" );
		btn34.setType( "view" );
		btn34.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=http%3a%2f%2f"+domain+"%2fxyguwen%2findex.html%23%2ffeedback%3fsource%3dmenu&response_type=code&scope=snsapi_userinfo&state=10003#wechat_redirect" );

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName( "我的" );
		mainBtn3.setSub_button( new Button[] { btn31 , btn32 , btn33 , btn34 } );

		/**
		 * 这是目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton( new Button[] { btn11 , mainBtn2 , mainBtn3 } );
		return menu;
	}

}