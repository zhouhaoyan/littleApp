// pages/imageList/imageList.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    banner_key:[],
    imgListKey:[],
    pageSize:4,
    pageNo:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      console.log("页面初始化")
      this.getBannerList();
      this.getImgList();
 
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
    console.log("监听页面显示")

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
    console.log("页面相关事件处理函数--监听用户下拉动作")
    this.cleanpage();
    this.getBannerList();
    this.getImgList();
    wx.stopPullDownRefresh();  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

    console.log("页面上拉触底事件的处理函数")
    var that = this;
    wx.request({
      url: 'http://47.104.142.33:8080/album/page/top?pageSize='+that.data.pageSize+'&pageNo='+(that.data.pageNo+1),
      success: function (res) {
        console.log(res.data.data.list);
        that.setData({
          imgListKey: that.data.imgListKey.concat(res.data.data.list),
          pageSize: res.data.data.pageSize,
          pageNo: res.data.data.pageNum
        })
      }
    })
  },

  getImgList:function(){
    var that=this;
    wx.request({
      url: 'http://47.104.142.33:8080/album/page/top?pageSize='+that.data.pageSize+'&pageNo='+that.data.pageNo,
      success: function (res) {
        console.log(res.data.data.list);
        that.setData({
          imgListKey: that.data.imgListKey.concat(res.data.data.list),
          pageSize: res.data.data.pageSize,
          pageNo:res.data.data.pageNum

        })
      }
    })
  },
  getBannerList: function () {
    var that = this;
    wx.request({
      url: 'http://47.104.142.33:8080/album/list/banner',
      success: function (res) {
        console.log(res.data.data);
        that.setData({
          banner_key: that.data.banner_key.concat(res.data.data)
        })
      }
    })
  },

  toAlbumPage:function(e){
    console.log("albumId:" + e.currentTarget.id);
    wx.navigateTo({
      url: '/pages/album/album?albumId=' + e.currentTarget.id
    })
  },

  cleanpage:function(){
      this.setData({
        banner_key: [],
        imgListKey: [],
        pageSize: 4,
        pageNo: 1
      })
  }
})