// pages/classify/classify.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    classifyKey:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getClassifyList();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  getClassifyList:function(){
    var that=this;
    wx.request({
      url: 'http://47.104.142.33:8080/classify/list',
      success:function(res){
          that.setData({
            classifyKey:res.data
          })
      }
    })
  },
  toList:function(e){
    console.log("classifyId:" + e.currentTarget.id);
    wx.navigateTo({
      url: '/pages/list/list?classifyId=' + e.currentTarget.id
    })
  }
})