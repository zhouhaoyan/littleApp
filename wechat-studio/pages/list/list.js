// pages/imageList/imageList.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgListKey: [],
    pageSize: 4,
    pageNo: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log("页面初始化" + options.classifyId)
    this.setData({
      classifyId: options.classifyId
    })
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
      url: getApp().data.server+'/album/page/classify?pageSize=' + that.data.pageSize + '&pageNo=' + (that.data.pageNo+1) + '&classifyId=' + that.data.classifyId,
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

  getImgList: function () {
    var that = this;
    wx.request({
      url: getApp().data.server+'/album/page/classify?pageSize=' + that.data.pageSize + '&pageNo=' + that.data.pageNo + '&classifyId=' + that.data.classifyId,
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

  toAlbumPage: function (e) {
    console.log("albumId:" + e.currentTarget.id);
    wx.navigateTo({
      url: '/pages/album/album?albumId=' + e.currentTarget.id
    })
  },

  cleanpage: function () {
    this.setData({
      imgListKey: [],
      pageSize: 4,
      pageNo: 1
    })
  }
})