<template>
  <div>
    <el-upload
        class="avatar-uploader"
        drag
        action="#"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :http-request="upload"
    >
      <img referrerpolicy ="never" v-if="imageUrl" :src="imageUrl" class="avatar" alt="">
      <!--      <img v-if="value" :src="value" class="avatar" alt="">-->
      <slot v-else name="icon-content">
        <i class="el-icon el-icon-upload"></i>
        <div class="el-upload__text">将图片拖到此处，或<br/>点击上传</div>
      </slot>
      <el-progress v-if="showPercentage" :percentage="percentage" status="success"/>
    </el-upload>
  </div>
</template>

<script>
const COS = require('cos-js-sdk-v5')
// 填写自己腾讯云cos中的key和id (密钥)
const cos = new COS({
  SecretId: ' ', // 身份识别ID
  SecretKey: ' ' // 身份秘钥
})

export default {
  name: 'UploadImg',
  props: {
    imgUrl: String,
  },
  data() {
    return {
      imageUrl: this.imgUrl,
      percentage: 0,
      showPercentage: false
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isImg = file.type === 'image/jpeg' || 'image/x-png' || 'image/png' || 'image/jpg'
      const isLt20M = file.size / 1024 / 1024 < 20

      if (!isImg) { // 限制格式 可以手动删掉
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt20M) {
        this.$message.error('上传头像图片大小不能超过 20MB!')
      }
      return isImg && isLt20M
    },
    upload(res) {
      if (res.file) {
        this.showPercentage = true// 开始上传进度条开始
        cos.putObject({
          Bucket: 'request404-1304131034', /* 存储桶 */
          Region: 'ap-guangzhou', /* 存储桶所在地域，必须字段 */
          Key: '/website-portrait/' + res.file.name, /* 文件名 */
          StorageClass: 'STANDARD', // 上传模式, 标准模式
          Body: res.file, // 上传文件对象
          //   进度条
          onProgress: (progressData) => {
            this.percentage = progressData.percent * 100 // progressData查看滚动条默认是0-1
          }
        }, (err, data) => {
          // 上传成功之后
          if (data.statusCode === 200) {
            this.imageUrl = `https:${data.Location}`
            this.$emit('callBack', `https:${data.Location}`)
            // console.log(`https:${data.Location}`)
          }
          this.showPercentage = false// 不管成功还是失败上传完成进度条结束
        })
      }
    }

  }
}
</script>

<style scoped>
.avatar-uploader /deep/ .el-upload {
  /*border: 1px solid #d9d9d9;*/
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

/*.avatar-uploader .el-upload:hover {*/
/*  border-color: #409EFF;*/
/*}*/
/deep/ .el-upload-dragger {
  font-weight: 100;
  color: #8c939d;
  width: auto;
  /*min-width: 178px;*/
  height: 178px;
}

.el-upload-dragger /deep/ .el-icon{
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 178px
}

/deep/ .el-upload__text {
  color: #8c939d;
}

.avatar {
  margin: auto;
  width: auto;
  min-width: 7rem;
  /*min-width: 178px;*/
  height: 178px;
  display: block;
}
</style>
