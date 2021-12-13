<template>
  <div>
    <el-upload
        class="avatar-uploader"
        action="#"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :http-request="upload"
    >
      <!-- <img v-if="imageUrl" :src="imageUrl" class="avatar"> -->
      <img v-if="value" :src="value" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon" />
      <el-progress v-if="showPercentage" :percentage="percentage" status="success" />
    </el-upload>
  </div>
</template>

<script>
const COS = require('cos-js-sdk-v5')
// 填写自己腾讯云cos中的key和id (密钥)
const cos = new COS({
  SecretId: 'AKIDCE2asvhDSUFjwsYy7zcy4tRijUzFEwMW', // 身份识别ID
  SecretKey: 'vKTMyJODm6AyWnpdCYeT1JL7crpWw6yZ' // 身份秘钥
})

export default {
  name: 'UploadImg',
  props: {
    value: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      imageUrl: '',
      percentage: 0,
      showPercentage: false
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) { // 限制格式 可以手动删掉
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    upload(res) {
      if (res.file) {
        // 执行上传操作
        console.log(res.file.name);
        this.showPercentage = true// 开始上传进度条开始
        cos.putObject({
          Bucket: 'request404-1304131034', /* 存储桶 */
          Region: 'ap-guangzhou', /* 存储桶所在地域，必须字段 */
          Key: '/website-portrait/'+res.file.name, /* 文件名 */
          StorageClass: 'STANDARD', // 上传模式, 标准模式
          Body: res.file, // 上传文件对象
          //   进度条
          onProgress: (progressData) => {
            console.log(JSON.stringify(progressData))
            this.percentage = progressData.percent * 100 // progressData查看滚动条默认是0-1
          }
        }, (err, data) => {
          console.log(err || data)
          // 上传成功之后
          if (data.statusCode === 200) {
            // this.imageUrl = `https:${data.Location}`
            this.$emit('input', `https:${data.Location}`)
            console.log(`https:${data.Location}`)
          }
          this.showPercentage = false// 不管成功还是失败上传完成进度条结束
        })
      }
    }

  }
}
</script>

<style scoped lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.progress {
  position: absolute;
  display: flex;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  background: #fff;
}
</style>
