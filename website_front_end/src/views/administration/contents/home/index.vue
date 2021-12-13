<template>
  <div id="home_page">
    <div id="user_info">
      <Modal
          title="修改信息"
          v-model="modal"
          :styles="{top: '33%'}">
        <Input v-model="userInfo.nickName" placeholder="Enter number" style="width: 80%; margin: 5px">
          <span slot="prepend">用户昵称：</span>
        </Input>
        <div style="width: 80%; margin: 15px 5px; color: rgba(22, 22, 22,.75); display: flex">
          性别：
          <RadioGroup v-model="userInfo.gender" style="display: flex;">
            <Radio class="model-radio" style="color: #1296db" label="男">
              <span class="model-icon">
                <img :src="manSvg" style="width: 1.2rem; height: 1.2rem" alt="">
              </span>
              <span>男</span>
            </Radio>
            <Radio id="woman_radio" class="model-radio" style="color: #db639b" label="女">
              <span class="model-icon">
                <img :src="womanSvg" style="width: 1.2rem; height: 1.2rem" alt="">
              </span>
              <span>女</span>
            </Radio>
          </RadioGroup>
        </div>
        <Input disabled v-model="userInfo.email" placeholder="Enter number" style="width: 80%; margin: 5px">
          <span slot="prepend">用户邮箱：</span>
        </Input>
        <Input v-model="userInfo.telephone" placeholder="Enter number" style="width: 80%; margin: 5px">
          <span slot="prepend">联系方式：</span>
        </Input>
        <div slot="footer">
          <Button type="success" size="large" :loading="modal_loading" @click="commit">提交</Button>
        </div>
      </Modal>
      <!--    {{userInfo}}-->
      <div id="avatar_frame">
        <img-upload :imgUrl="userInfo.headPortrait===null||''?defaultAvatar:userInfo.headPortrait" @callBack="callBack">
        </img-upload>
      </div>
      <div id="user_info_form">
        <img @click="modal = true" id="alter_icon" :src="alterSvg" alt="">
        <ul id="user_info_ul">
          <li class="user-info-li">
            <span>
              <b>用户名：</b>{{ userInfo.username }}
              <img :src="userInfo.gender==='男'?manSvg:womanSvg" alt="">
            </span>
          </li>
          <li class="user-info-li">
            <span><b>用户昵称：</b>{{ userInfo.nickName }}</span>
          </li>
          <li class="user-info-li">
            <span><b>邮箱地址：</b>{{ userInfo.email }}</span>
          </li>
          <li class="user-info-li">
            <span><b>联系方式：</b>{{ userInfo.telephone }}</span>
          </li>
          <li class="user-info-li">
            <span><b>用户身份：</b>{{ userInfo.role }}</span>
          </li>
          <li class="user-info-li">
            <span><b>用户权限：</b>{{ userInfo.authentication }}</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import imgUpload from '../../../../components/img-upload';
import defaultAvatar from '@/assets/img/home/avatar.gif';
import manSvg from '../../../../assets/img/svg/man.svg';
import womanSvg from '../../../../assets/img/svg/woman.svg';
import alterSvg from '../../../../assets/img/svg/alter.svg';

export default {
  name: "index",
  data() {
    return {
      userInfo: null,
      modal: false,
      modal_loading: false,
      defaultAvatar,
      manSvg,
      womanSvg,
      alterSvg
    }
  },
  components: {imgUpload},
  methods: {
    commit() {
      this.modal_loading = true;
      setTimeout(() => {
        this.modal_loading = false;
        this.api.userAdminUserInfoUpdate(this.userInfo).then(rs=>{
          if(rs.data.data){
            sessionStorage.setItem("website-user-info", JSON.stringify(this.userInfo));
            location.reload();
          }
        })
      }, 2000);
    },
    callBack(imgUrl){
      this.userInfo.headPortrait = imgUrl
      this.api.userAdminUserInfoUpdate(this.userInfo).then(rs=>{
        if(rs.data.data){
          sessionStorage.setItem("website-user-info", JSON.stringify(this.userInfo));
          location.reload();
        }
      })
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem("website-user-info"))
  }
}
</script>

<style scoped>
/deep/ .el-icon {
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 50px;
}

#home_page {
  width: 100%;
  height: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url("../../../../assets/img/administration/user_home_background.jpg");
  /*background-size: auto 100%;*/
  /*background-position: center center;*/
}

#user_info {
  width: auto;
  display: flex;
  padding: 2.5rem;
  box-sizing: content-box;
  background-color: rgba(255, 255, 255, .85);
  box-shadow: 0 0 5px rgba(122, 122, 122, .55);
  border-radius: 8px;
  position: relative;
}

#avatar_frame {
  width: 7.8rem;
  height: 7.8rem;
  margin-right: 3.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border-radius: 50%;
  box-sizing: content-box;
  transform: scale(1.2, 1.2);
  position: relative;
  top: 1rem;
  box-shadow: 0 0 5px rgba(122, 122, 122, .35);
  /*border: 5px solid #7aba01;*/
}

#user_info_form {
  width: auto;
}

#alter_icon {
  width: 1.5rem;
  height: auto;
  position: absolute;
  top: 5px;
  right: 5px;
  cursor: pointer;
}

#user_info_ul {
  list-style: none;
  margin: 0;
  padding: 0;
  cursor: default;
}

.user-info-li {
  padding: 0 1.2rem 1.2rem .5rem;
  display: flex;
  /*justify-content: center;*/
  align-items: center;
}

.user-info-li > span {
  font-size: 1.1rem;
  color: rgba(22, 22, 22, .95);
}

.user-info-li > span > img {
  width: 1.5rem;
  height: 1.5rem;
  overflow: hidden;
  position: relative;
  top: 5px;
  left: 2px;
}

.model-radio {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 .5rem;
}

/deep/ .ivu-radio {
  padding-top: 3px;
}

/deep/ #woman_radio .ivu-radio-inner {
  border-color: #db639b;
}

/deep/ #woman_radio .ivu-radio-inner:nth-of-type(1):after {
  background-color: #db639b;
}

.model-icon {
  width: 1.2rem;
  height: 1.2rem;
  /*background-color: #008489;*/
  display: flex;
  justify-content: center;
  align-items: center;
}

/*.vertical-center-modal{*/
/*  display: flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*}*/

/*.vertical-center-modal .ivu-modal{*/
/*  top: 0;*/
/*}*/
</style>
