const qiniuUploader = require("../../utils/qiniuUploader");

Page({
  data: {
    files: [],
    imgUrls:[],
    progress:0,
    isBanner:1,
    isTop:1,
    albumUrl:"",
    inputValue:'',
    classifyList:[],
    classifyIndex:-1,
    classifyId:0,
    name:"",
    title:"",
    description:""
    
  },
  onLoad: function () {
    console.log('onLoad')
    this.clean();
    initQiniu();
    this.getClassifyList();

  },
  onShow:function(){
    console.log('onShow')
  },
  openToast: function () {
    wx.showToast({
      title: '已完成',
      icon: 'success',
      duration: 3000
    });
  },
  openLoading: function () {
    wx.showToast({
      title: '图片上传中',
      icon: 'loading',
      duration: 3000
    });
  },
  chooseImage: function () {
      var that=this;
    wx.chooseImage({
      count:9,
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        var filePathList = res.tempFilePaths;

        for (var i = 0; i < filePathList.length;i++){
        // 交给七牛上传
          console.log("index:" + i + " and path:" + filePathList[i]);
        var filePath = filePathList[i];
        qiniuUploader.upload(filePath, (res) => {
          that.setData({
            files: that.data.files.concat(res.imageURL),
            imgUrls: that.data.imgUrls.concat(res.imageURL)
       
          });

          if (i == 0) {
            console.log("index:"+i);
            this.setData({
              albumUrl: res.imageUR
            }
            )
           
          }
        }, (error) => {
          console.error('error: ' + JSON.stringify(error));
        },
          null,// 可以使用上述参数，或者使用 null 作为参数占位符
          (progress) => {
            console.log('上传进度', progress.progress)
            console.log('已经上传的数据长度', progress.totalBytesSent)
            console.log('预期需要上传的数据总长度', progress.totalBytesExpectedToSend)
            that.setData({
              progress: progress.progress
            })
          }
        );
      }
      }
    })
  },
  previewImage: function (e) {
    console.log("preview:" + e.currentTarget.id);
    wx.previewImage({
      current: e.currentTarget.id, // 当前显示图片的http链接
      urls: this.data.files // 需要预览的图片http链接列表
    })
  },

  addAlbum:function(){
    if (!this.addCheck()) return ;
    var that=this;
    console.log("do save album")
    wx.request({
      url: 'http://47.104.142.33:8080/album/add',
      method:'POST',
      data:{
        "classifyId": this.data.classifyId,
        "name":this.data.name,
        "title":this.data.title,
        "description":this.data.description,
        "imgUrls": this.data.imgUrls,
        "isBanner":this.data.isBanner,
        "isTop":this.data.isTop
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success:function(res){
        console.log(res.data);
        that.clean();
        wx.showToast({
          title: '成功',
          icon: 'success',
          duration: 2000
        })
      },
      fail:function(res){
        console.log("新增相册失败")
      }
    })

  },
  bindName:function(e){
    this.setData({
      name:e.detail.value
    })
  },
  bindTitle:function(e){
    this.setData({
      title: e.detail.value
    })
  },
  bindDescription:function(e){
    this.setData({
      description: e.detail.value
    })
  },
  bannerSwitch:function(e){
    console.log("bannerSwitch:"+e.detail.value);
    if(e.detail.value==true){
        this.setData({
          isBanner:1
        })
    }else{
      this.setData({
        isBanner: 0
      })
    }
  },
  topSwitch:function(e){
    console.log("topSwitch"+e.detail.value);
    if (e.detail.value == true) {
      this.setData({
        isTop: 1
      })
    } else {
      this.setData({
        isTop: 0
      })
    }
  },
  bindPickerChange:function(e){
    
    console.log("所选择分类ID:" + this.data.classifyList[e.detail.value].id);
    this.setData({
      classifyIndex: e.detail.value,
      classifyId: this.data.classifyList[e.detail.value].id
    })
   
  },
  getClassifyList: function () {
    var that = this;
    wx.request({
      url: 'http://47.104.142.33:8080/classify/list',
      success: function (res) {
        that.setData({
          classifyList: res.data
        })
      }
    })
  },
  clean:function(){
      this.setData({
        files: [],
        imgUrls: [],
        progress: 0,
        isBanner: 1,
        isTop: 1,
        inputValue: ''

      })
  },
  addCheck: function () {

    if (this.data.classifyId==0){
      this.showAlert('请选择类别');
      return false;;
    }
    if (this.data.name == "") {
      this.showAlert('请输入相册名称');
      return false;
    } 


    if (this.data.title == "") {
      this.showAlert('请输入标题');
      return false;
    }

    if (this.data.description == "") {
      this.showAlert('请输入相册描述');
      return false;
    }
    
    return true;
      // "classifyId":this.data.classifyId ,
      // "name":this.data.name,
      //   "title":this.data.title,
      //     "description":this.data.description,
      //       "imgUrls": this.data.imgUrls,
      //         "isBanner":this.data.isBanner,
      //           "isTop":this.data.isTop
  },
  showAlert: function (content) {
    wx.showModal({
      content: content,
      showCancel: false,
      success: function (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        }
      }

    });
}

  
});




// 初始化七牛相关参数
function initQiniu() {
  var options = {
    region: 'ECN', // 华东区
    uptokenURL: 'http://47.104.142.33:8080/upload/uploadToken',
    //uptoken: token,
    domain: 'http://oxllb2jb0.bkt.clouddn.com',
    shouldUseQiniuFileName: false
  };
  qiniuUploader.init(options);
}