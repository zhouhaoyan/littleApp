//app.js
App({
  data: {
  //server: "http://127.0.0.1:8080",
    server: "https://wechat.loglife.club",
    studioToken:"c4ca4238a0b923820dcc509a6f75849b",
    version: "1.0.2",
  },
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    var that = this;
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        console.log("res.code:" + res.code);
        this.globalData.code = res.code
        this.getSessionKey(this.globalData.code);
        
      }
    })
 
    // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       console.log("获取用户信息")
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo
    //           this.getSessionKey(this.globalData.code);
            
    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     }
    //   }
    // })
  },
  globalData: {
    userInfo: null,
    openid:null,
    code:null,
  },
  getSessionKey: function (userCode) {
    var that=this;
    wx.request({
      url: getApp().data.server + '/user/login?studioToken='+getApp().data.studioToken+'&code=' + userCode + '&version=' + getApp().data.version,
      success: function (res) {
        console.log(res.data)
        that.globalData.openid = res.data.openid;
        //that.updateUserInfo();
          
      }
    })
  },
  updateUserInfo:function() {
    console.log("openId:" + this.globalData.openid);
    wx.request({
      url: getApp().data.server +'/user/userInfo',
      method:'POST',
      data: {
        openId: this.globalData.openid,
        nickName: this.globalData.userInfo.nickName,
        avatarUrl: this.globalData.userInfo.avatarUrl,
        gender: this.globalData.userInfo.gender, //性别 0：未知、1：男、2：女
        province: this.globalData.userInfo.province,
        city: this.globalData.userInfo.city,
        country: this.globalData.userInfo.country,
      },
      success:function(res){
        console.log("更新新用户信息成功!");
      }
    })
  }
}

)