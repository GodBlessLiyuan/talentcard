#logo图
路径在web的配置文件里
#菜单创建接口：自定义菜单 - 创建接口
https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
#JSON模版：
 {
     "button":[
     {
         "name":"一级菜单一",
         "type":"view",
          "url":"http://www.baidu.com"

      },
      {
           "name":"衢江人才卡",
           "sub_button":[
           {
               "type":"view",
               "name":"我的人才卡",
               "url":"http://dev.localcards.gov.vbooster.cn/wx/"
            },
        	{
               "type":"view",
               "name":"人才卡申请",
               "url":"http://dev.localcards.gov.vbooster.cn/wx/"
            }]
       }]
 }