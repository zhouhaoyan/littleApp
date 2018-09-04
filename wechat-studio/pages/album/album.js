
let col1H = 0;
let col2H = 0;

// pages/album/album .js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrollH: 0,
    imgWidth: 0,
    loadingCount: 0,
    images: [],
    col1: [],
    col2: [],
    imageUrls:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.getSystemInfo({
      success: (res) => {
        let ww = res.windowWidth;
        let wh = res.windowHeight;
        let imgWidth = ww * 0.48;
        let scrollH = wh;

        this.setData({
          scrollH: scrollH,
          imgWidth: imgWidth
        });

        //加载首组图片
        this.loadImages(options.albumId);
      }
    })

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

   onImageLoad: function (e) {
    let imageId = e.currentTarget.id;
    let oImgW = e.detail.width;         //图片原始宽度
    let oImgH = e.detail.height;        //图片原始高度
    let imgWidth = this.data.imgWidth;  //图片设置的宽度
    let scale = imgWidth / oImgW;        //比例计算
    let imgHeight = oImgH * scale;      //自适应高度

    let images = this.data.images;
  
    let imageObj = null;

    for (let i = 0; i < images.length; i++) {
      let img = images[i];
      console.log(img);
      if (img.id === imageId) {
        imageObj = img;
        break;
      }
    }
    
    imageObj.height = imgHeight;

    let loadingCount = this.data.loadingCount - 1;
    let col1 = this.data.col1;
    let col2 = this.data.col2;

    //判断当前图片添加到左列还是右列
    if (col1H <= col2H) {
      col1H += imgHeight;
      col1.push(imageObj);
    } else {
      col2H += imgHeight;
      col2.push(imageObj);
    }

    let data = {
      loadingCount: loadingCount,
      col1: col1,
      col2: col2
    };

    //当前这组图片已加载完毕，则清空图片临时加载区域的内容
    if (!loadingCount) {
      data.images = [];
    }

    this.setData(data);
  },

  loadImages: function (albumId) {
    // let images = [
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/1.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/2.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/3.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/4.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/5.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/6.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/7.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/8.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/9.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/10.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/11.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/12.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/13.png", height: 0 },
    //   { pic: "http://oxllb2jb0.bkt.clouddn.com/14.png", height: 0 }
    // ];

    // let baseId = "img-" + (+new Date());

    // for (let i = 0; i < images.length; i++) {
    //   images[i].id = baseId + "-" + i;
    // }

    // console.log("imageLength:"+images.length)
    // console.log("imimages:" + images);
    // this.setData({
    //   loadingCount: images.length,
    //   images: images
    // });

    this.getAlbumImgs(albumId);
    this.resourceLook(albumId,"ALBUM");
  },

  getAlbumImgs: function (albumId){
    var that=this;
    wx.request({
      url: getApp().data.server+'/resources/albumPage?albumId='+ albumId+'&pageSize=20&pageNo=1',
      success:function(res){
        let baseId = "img-" + (+new Date());
        var list=res.data.data.list;
        let resultList=[];
        let urlList=[];
        for(var i=0;i<list.length;i++){
          resultList.push({ id: baseId+"-test"+list[i].id, pic:list[i].url,height:0,rId:list[i].id});
          urlList.push(list[i].url);
        }
        console.log(resultList);
        that.setData({
          loadingCount: resultList.length,
          images: resultList,
          imageUrls:urlList
        });
      }
    })
  },
  resourceLook:function(albumId,type){
    wx.request({
      url: getApp().data.server + '/resourcesLog/look?studioToken='+getApp().data.studioToken+'&openId=' + getApp().globalData.openid+'&rId='+ albumId+'&type='+type,
      success:function(res){
        console.log("埋点 sucess, albumId: "+albumId)
      }
    })
  },

  previewImg:function(e){
    console.log(e);
    this.resourceLook(e.target.dataset.rid,"RESOURCE")
      wx.previewImage({
        current:e.target.id,
        urls: this.data.imageUrls,

      })

  }

})