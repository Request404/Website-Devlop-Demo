<template>
  <div id="sign_up_framework" :style="'background-image: url('+ bkImgUrl +')'">
    <header>
      <figure @click="routerTo('/home')" id="logo">
        <img :src="logoImg" alt="">
      </figure>

      <div id="lang_change">
        <language-select>
          <template v-slot:icon>
            <img :src="langSvg" alt="">
          </template>
        </language-select>
      </div>
    </header>
    <div v-if="step === 'sendCode'" class="form-pane">
      <h1>{{ $t('account.alterPassword') }}</h1>
      <div ref="email" class="input-frame">
        <label class="input-label" for="account_email">
          <span>{{ $t('account.username') }}:</span>
          <input @keyup.enter="sendCode" v-model="email" class="input-pane" id="account_email" type="text"
                 :placeholder="$t('account.alterPlaceholder')">
        </label>
      </div>
      <button @click="sendCode" :class="emailPassVerify?'sign-up sign-up-active':'sign-up'">{{ $t('account.sendVerifyCode') }}</button>
      <div class="other-choose">
        <p @click="routerTo('/account/sign-up')">{{ $t('account.toSignUp') }}</p>
        <p @click="routerTo('/account/sign-in')">{{ $t('account.returnSignIn') }}</p>
      </div>
    </div>
    <div v-else-if="step === 'alterPassword'" class="form-pane">
      <h1>{{ $t('account.changePassword') }}</h1>
      <div ref="password" class="input-frame">
        <label class="input-label" for="account_password">
          <span>{{ $t('account.password') }}:</span>
          <input @keyup.enter="sendAlter" v-model="password" class="input-pane" id="account_password" type="password">
        </label>
      </div>
      <div ref="confirm" class="input-frame">
        <label class="input-label" for="account_confirm">
          <span>{{ $t('account.confirm') }}:</span>
          <input @keyup.enter="sendAlter" v-model="confirm" class="input-pane" id="account_confirm" type="password">
        </label>
      </div>
      <div ref="verifyCode" class="input-frame">
        <label class="input-label" for="account_verify_code">
          <span>{{ $t('account.verifyCode') }}:</span>
          <input @keyup.enter="sendAlter" maxlength="6" v-model="verifyCode" class="input-pane" id="account_verify_code" type="text">
        </label>
      </div>
      <button @click="sendAlter" :class="infoPassVerify?'sign-up sign-up-active':'sign-up'">{{ $t('account.beginAlter') }}</button>
      <div class="other-choose">
        <p @click="routerTo('/account/sign-up')">{{ $t('account.toSignUp') }}</p>
        <p @click="routerTo('/account/sign-in')">{{ $t('account.returnSignIn') }}</p>
      </div>
    </div>
    <div v-else class="form-pane">
      <img style="border-radius: 5px; box-shadow: 0 0 10px rgba(22, 22, 22,.38)" :src="finishImg"
           alt="">
      <button @click="routerTo('/account/sign-in')" class="sign-up finish-active">{{ $t('account.finish') }}</button>
    </div>
  </div>
</template>

<script>
import languageSelect from '@/components/language-select'

import logoImg from '@/assets/img/common/logo.png'
import langSvg from '@/assets/img/svg/language_green.svg';
import finishImg from '@/assets/img/account/alter-password/finish.gif';
import bkImgUrl from '@/assets/img/account/alter-password/background-img-ai.png';
import arrowSvg from '@/assets/img/svg/sign-in.svg';

export default {
  name: "index",
  data() {
    return {
      logoImg, langSvg, bkImgUrl, arrowSvg, finishImg,
      step: 'sendCode',
      email: '',
      password: '',
      confirm: '',
      verifyCode: '',
      emailPassVerify: false,
      passwordPassVerify: false,
      confirmPassVerify: false,
      verifyCodePassVerify: false,
      infoPassVerify: false,
    }
  },
  components: {
    languageSelect
  },
  methods: {
    routerTo(path) {
      this.$store.commit("needLoading", true)
      setTimeout(() => {
        this.$store.commit("needLoading", false)
        this.$router.push(path)
      }, 1500)
    },
    sendCode() {
      if (this.emailPassVerify) {
        this.api.userVerifyCodeTwo(this.email, this.utils.getDeviceInfo()).then(res => {
          if (res.data.code === 200) {
            this.email = res.data.data
            this.$Message.success("验证码发送成功");
            this.step = 'alterPassword'
          } else {
            if (res.data.data === -1) {
              this.$Message.error({
                content: '该用户不存在，请重新确认',
                duration: 5,
                closable: true
              });
            } else {
              this.$Message.error({
                content: '邮件发送失败',
                duration: 5,
                closable: true
              });
            }
            this.email = '';
          }
        })
      }
    },
    sendAlter() {
      if (this.infoPassVerify) {
        let user = {
          email: this.email,
          password: this.password,
          verifyCode: this.verifyCode
        }
        this.api.userPasswordAlter(user).then(res => {
          if (res.data.code === 200) {
            this.$Message.info({
              content: res.data.msg,
              duration: 10,
              closable: true
            });
            this.step = 'finish';
          } else {
            if (res.data.data === -1) {
              this.$Message.error({
                content: '该用户不存在，请重新确认',
                duration: 5,
                closable: true
              });
            } else if (res.data.data === -2) {
              this.$Message.error({
                content: '验证码错误',
                duration: 10,
                closable: true
              });
            } else {
              this.$Message.error({
                content: '密码修改失败！',
                duration: 10,
                closable: true
              });
            }
          }
        })
      }
    },
  },
  updated() {
    this.emailPassVerify = this.email.length >= 5;
    if (this.password === '') {
      this.passwordPassVerify = false
      this.$refs.password.style.borderColor = "rgba(22, 22, 22, .8)";
    } else if (this.password.length >= 6) {
      this.passwordPassVerify = true
      this.$refs.password.style.borderColor = "rgba(12, 175, 77, .88)";
    } else {
      this.passwordPassVerify = false
      this.$refs.password.style.borderColor = "rgba(204, 41, 0,.88)";
    }
    if (this.confirm === '') {
      this.confirmPassVerify = false
      this.$refs.confirm.style.borderColor = "rgba(22, 22, 22, .8)";
    } else if (this.confirm === this.password) {
      this.confirmPassVerify = true
      this.$refs.confirm.style.borderColor = "rgba(12, 175, 77, .88)";
    } else {
      this.confirmPassVerify = false
      this.$refs.confirm.style.borderColor = "rgba(204, 41, 0,.88)";
    }
    if (this.verifyCode === '') {
      this.verifyCodePassVerify = false
      this.$refs.verifyCode.style.borderColor = "rgba(22, 22, 22, .8)";
    } else if (this.verifyCode.length === 6) {
      this.verifyCodePassVerify = true
      this.$refs.verifyCode.style.borderColor = "rgba(12, 175, 77, .88)";
    } else {
      this.verifyCodePassVerify = false
      this.$refs.verifyCode.style.borderColor = "rgba(204, 41, 0,.88)";
    }
    this.infoPassVerify = this.passwordPassVerify && this.confirmPassVerify && this.verifyCodePassVerify;
  }
}
</script>

<style scoped lang="scss">
#sign_up_framework {
  width: 100%;
  height: 100%;
  position: relative;
  background-position: center center;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;

  header {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: auto;
    display: flex;
    justify-content: space-between;
    align-items: center;

    #logo {
      width: auto;
      padding-left: .5rem;
      height: 5.5rem;
      cursor: pointer;

      img {
        width: auto;
        height: 100%;
      }
    }
  }

  .form-pane {
    width: 30%;
    min-height: 47.5%;
    //height: 70%;
    padding: 2rem 0;
    background-color: rgba(240, 244, 240, .85);
    border-radius: 1rem;
    box-shadow: 0 0 15px rgba(66, 66, 66, .5);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    animation: 1.25s linear moveAnim;

    h1 {
      font-size: 2.5rem;
      text-transform: uppercase;
      letter-spacing: .2rem;
      margin: 0;
      margin-bottom: 1rem;
      text-shadow: 0 0 2px rgba(66, 66, 66, .35);
      user-select: none;
    }

    .input-frame {
      width: 70%;
      height: auto;
      margin: .5rem 0;
      border-radius: .5rem;
      border: 2px solid rgba(22, 22, 22, .8);
      background-color: rgba(255, 255, 255, .8);
      transition: .5s ease;

      .input-label {
        display: flex;
        flex-direction: column;
        padding: .35rem .5rem;
        font-weight: bold;
        font-size: 1.2rem;
        cursor: pointer;
      }

      .input-pane {
        outline: none;
        border: none;
        padding: .2rem;
        background-color: rgba(255, 255, 255, .8);

        &::placeholder {
          font-size: 1rem;
          color: rgba(122, 122, 122, .85);
        }
      }

      span {
        margin-left: 4px;
      }
    }

    .agreement-checkbox {
      margin: 1rem 0;
      width: 70%;
      //background-color: #008489;
    }

    .sign-up {
      width: 70%;
      height: 3rem;
      margin: 1.2rem 0;
      color: #484848;
      border: 2px solid #484848;
      border-radius: .8rem;
      cursor: pointer;
      font-size: 1.2rem;
      font-weight: bold;
      background-color: white;
      transition: .5s ease;
    }

    .sign-up-active {
      background-color: #7aba01;
      border: 2px solid #7aba01;
      color: white;
    }

    .finish-active {
      width: 43%;
      margin-top: 1.5rem;
      background-color: #7aba01;
      border: 2px solid #7aba01;
      color: white;
      box-shadow: 0 0 5px rgba(0, 0, 0, .38);
    }

    .other-choose {
      margin-top: .5rem;
      display: flex;

      p {
        font-size: 1rem;
        font-weight: bold;
        margin: 0 .5rem;
        cursor: pointer;
        color: rgba(122, 122, 122, .85);
        transition: .75s ease;

        &:hover {
          color: #000000;
        }
      }
    }
  }
}

@media screen and (max-width: 1400px) {
  #sign_up_framework {

    header {

      #logo {
        height: 4.8rem;
      }
    }

    .form-pane {
      width: 45%;
      //height: 65%;

    }
  }
}

@media screen and (max-width: 1024px) {
  #sign_up_framework {

    header {
      #logo {
        height: 4.8rem;
      }
    }

    .form-pane {
      width: 60%;
      //height: 65%;
    }
  }
}

@media screen and (max-width: 600px) {
  #sign_up_framework {

    header {

      #logo {
        height: 4.5rem;
      }
    }

    .form-pane {
      width: 90%;
      //height: 55%;

      h1 {
        font-size: 2rem;
      }

      .input-frame {
        width: 90%;
        margin: .2rem 0;

        .input-label {
          font-size: 1.1rem;
          padding: .15rem .35rem;
        }

        span {
          margin-left: 4px;
        }
      }

      .agreement-checkbox {
        margin: .8rem 0;
        width: 90%;
      }

      .sign-in {
        width: 4rem;
        height: 4rem;
        margin: .2rem 0;
      }

      .other-choose {
        margin-top: .5rem;
        display: flex;

        p {
          font-size: .9rem;
          margin: 0 .5rem;

          &:hover {
            color: #000000;
          }
        }
      }
    }
  }
}

@media screen and (max-width: 350px) {
  #sign_up_framework {

    header {

      #logo {
        height: 4.5rem;
      }
    }

    .form-pane {
      width: 88%;
      //height: 60%;

      h1 {
        font-size: 1.5rem;
      }

      .input-frame {
        width: 88%;

        .input-label {
          font-size: .95rem;
        }

        span {
          margin-left: 4px;
        }
      }

      .agreement-checkbox {
        margin: .5rem 0;
        width: 90%;
      }

      .sign-in {
        width: 3.75rem;
        height: 3.75rem;
      }

      .other-choose {
        margin-top: .5rem;
        display: flex;

        p {
          font-size: .8rem;
          margin: 0 .5rem;

          &:hover {
            color: #000000;
          }
        }
      }
    }
  }
}

@media screen and (max-height: 740px) {
  #sign_up_framework {

    header {

      #logo {
        height: 4.5rem;
      }
    }

    .form-pane {
      width: 88%;
      //height: 60%;
      padding: .5rem 0;

      h1 {
        font-size: 1.5rem;
        //margin: .5rem 0;
      }

      .input-frame {
        width: 88%;

        .input-label {
          font-size: .9rem;
        }

        span {
          margin-left: 4px;
        }
      }

      .agreement-checkbox {
        margin: .5rem 0;
        width: 88%;
      }

      .sign-up {
        margin: .25rem 0;
        height: 2.5rem;
        font-size: 1rem;
      }

      .other-choose {
        margin-top: .5rem;
        display: flex;

        p {
          font-size: .8rem;
          margin: 0 .5rem;

          &:hover {
            color: #000000;
          }
        }
      }
    }
  }
}

@keyframes moveAnim {
  0% {
    position: relative;
    top: 2%;
  }
  50% {
    position: relative;
    top: -3%;
  }
  90% {
    position: relative;
    top: 1%;
  }
  100% {
    position: relative;
    top: 0;
  }
}
</style>
