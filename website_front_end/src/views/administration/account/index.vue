<template>
  <div id="account_pane">
    <div id="account_form">
      <el-card class="box-card" v-if="visited === 'login'">
        <h3 class="system_title">Request404 管理端</h3>
        <el-input @keyup.enter.native="login" placeholder="请输入用户名" v-model="username">
          <template slot="prepend">账号</template>
        </el-input>
        <el-input @keyup.enter.native="login" placeholder="请输入密码" v-model="password" show-password>
          <template slot="prepend">密码</template>
        </el-input>
        <el-checkbox v-model="checked">记住我</el-checkbox>
        <a id="forget_password" href="#" @click="alterPassword">忘记密码？</a>
        <div class="button_wrap">
          <el-button @click="login" type="primary" round>登录</el-button>
        </div>
      </el-card>
      <el-card class="box-card" v-if="visited === 'alter'">
        <h3 class="system_title">Request404 修改密码</h3>
        <div style="margin-bottom: 2rem">
          <el-steps :active="active" align-center finish-status="success">
            <el-step title="" icon="el-icon-message"></el-step>
            <el-step title="" icon="el-icon-edit"></el-step>
            <el-step title="" icon="el-icon-circle-check"></el-step>
          </el-steps>
        </div>
        <div class="step-frame" v-if="active === 0">
          <el-input @keyup.enter.native="sendCode" placeholder="请输入账号/邮箱" v-model="email">
            <template slot="prepend">账号</template>
          </el-input>
          <el-button type="success" style="margin-top: 12px;" @click="sendCode">发送验证码</el-button>
        </div>
        <div class="step-frame" v-if="active === 1">
          <el-input  @keyup.enter.native="sendAlter" placeholder="请输入密码" v-model="alter" show-password>
            <template slot="prepend">密码</template>
          </el-input>
          <el-input  @keyup.enter.native="sendAlter" placeholder="再次输入密码以确认" v-model="confirm" show-password>
            <template slot="prepend">确认</template>
          </el-input>
          <el-input  @keyup.enter.native="sendAlter" placeholder="请输入收到的验证码" maxlength=6 v-model="verifyCode">
            <template slot="prepend">验证</template>
          </el-input>
          <el-button type="success" style="margin-top: 12px;" @click="sendAlter">修改密码</el-button>
        </div>
        <div class="step-frame" v-if="active === 2">
          <el-button type="success" style="margin-top: 12px;" @click="CompleteAlter">完成</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>

export default {
  name: "index",
  data() {
    return {
      visited: 'login',
      username: '',
      password: '',
      email: '',
      alter: '',
      confirm: '',
      verifyCode: '',
      active: 0,
      checked: false,
    }
  },
  methods: {
    login() {
      if (this.username !== '' && this.password !== '') {
        this.api.userLogin(this.username, this.password).then(res => {
          if (res.data.code === 200) {
            let user = {
              username: this.username,
              password: this.password
            }
            if (this.checked) {
              localStorage.setItem("website-user", JSON.stringify(user));
            } else {
              localStorage.removeItem("website-user");
            }
            this.api.userInfo({
              method: 'get',
              url: '/user/info',
            }).then(rs =>{
              if(rs.data.data){
                if(rs.data.data.role==='user'){
                  sessionStorage.clear();
                  this.$message({
                    showClose: true,
                    message: '401，非管理员用户',
                    type: 'error'
                  });
                }else{
                  sessionStorage.setItem("website-user-info", JSON.stringify(rs.data.data));
                  this.$router.push('/administration/home');
                }
              }else{
                this.$message({
                  showClose: true,
                  message: '登录过程出现问题，请重新登录',
                  type: 'warning'
                });
              }
            })
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg + ',' + res.data.data,
              type: 'error'
            });
          }
        });
      } else {
        this.$message({
          showClose: true,
          message: '用户名或密码不能为空',
          type: 'warning'
        });
      }
    },
    alterPassword() {
      this.$store.commit('needLoading', true);
      setTimeout(() => {
        this.visited = 'alter';
        this.$store.commit('needLoading', false);
      }, 1000)
    },
    sendCode() {
      if (this.email !== '') {
        this.api.userVerifyCodeTwo(this.email, this.utils.getDeviceInfo()).then(res => {
          if (res.data.code === 200) {
            this.email = res.data.data
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'success'
            });
            this.active = 1
          } else {
            if (res.data.data === -1) {
              this.$message({
                showClose: true,
                message: '用户不存在！',
                type: 'error'
              });
            } else {
              this.$message({
                showClose: true,
                message: '邮件发送失败！',
                type: 'error'
              });
            }
            this.email = '';
          }
        })
      } else {
        this.$message({
          showClose: true,
          message: '邮箱地址不能为空',
          type: 'warning'
        });
      }
    },
    sendAlter() {
      if (this.alter === '' || this.confirm === '') {
        this.$message({
          showClose: true,
          message: '密码不能为空',
          type: 'warning'
        });
      } else if (this.verifyCode === '') {
        if (this.alter === '') {
          this.$message({
            showClose: true,
            message: '验证码不能为空',
            type: 'warning'
          });
        }
      } else if (this.alter !== this.confirm) {
        this.$message({
          showClose: true,
          message: '两次密码输入不一致',
          type: 'warning'
        });
      } else {
        let user = {
          email: this.email,
          password: this.alter,
          verifyCode: this.verifyCode
        }
        this.api.userPasswordAlter(user).then(res => {
          if (res.data.code === 200) {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'success'
            });
            this.active = 2
          } else {
            if (res.data.data === -1) {
              this.$message({
                showClose: true,
                message: '用户不存在！',
                type: 'error'
              });
            } else if (res.data.data === -2) {
              this.$message({
                showClose: true,
                message: '验证码错误！',
                type: 'error'
              });
            } else {
              this.$message({
                showClose: true,
                message: '密码修改失败！',
                type: 'error'
              });
            }
          }
        })
      }
    },
    CompleteAlter() {
      this.$store.commit('needLoading', true);
      setTimeout(() => {
        this.visited = 'login';
        this.$store.commit('needLoading', false);
        this.active = 0;
      }, 1000)
    }
  },
  created() {
    let user = JSON.parse(localStorage.getItem("website-user"));
    if (user) {
      this.username = user.username;
      this.password = user.password;
      this.checked = true;
    }
  }
}
</script>

<style scoped lang="scss">
#account_pane {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
  background-image: url("../../../assets/img/administration/home_background.jpg");
  background-size: 100% auto;
  background-position: center center;

  #account_form {
    width: 23%;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    opacity: 0.9;

    .box-card {

      #forget_password {
        position: relative;
        //background-color: #7aba01;
        left: 9rem;
      }

      .step-frame {
        width: 80%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        margin: auto;
      }
    }
  }
}

.system_title {
  text-align: center;
  margin: .5rem 0 1.5rem;
}

.el-input {
  margin-bottom: 1rem;
}

.el-checkbox {
  margin: 1rem;
}

.button_wrap {
  width: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
}

.el-button {
  width: 60%;
}


@media screen and (max-width: 1000px) {
  #account_pane {

    #account_form {
      width: 35%;
      height: auto;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-content: center;
    }
  }
}
</style>
