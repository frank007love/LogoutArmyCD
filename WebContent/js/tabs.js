
Ext.onReady(function(){
    // second tabs built from JS
    var tabs2 = new Ext.TabPanel({
        renderTo: document.body,
        activeTab: 0,
        plain:true,
        autoHeight: true,
        defaults:{autoScroll: false},
        items:[{
                title: '我的計時器',
                html: "My content was added during construction."
            },{
                title: '登出排行榜',
                autoLoad:'http://www.yahoo.com.tw'
            },{
                title: '邀請朋友',
                html:'<iframe src =\"inviteFriend.jsp" width=\"100%\" height=\"700\" border=\"0\"><p>Your browser does not support iframes.</p></iframe>'
            }
        ]
    });
});
