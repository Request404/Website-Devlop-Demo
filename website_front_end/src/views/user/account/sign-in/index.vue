<template>
  <div id="sign_in_framework" :style="'background-image: url('+ bkImgUrl +')'">
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
    <div class="form-pane">
      <h1>{{ $t('account.signIn') }}</h1>
      <div class="input-frame">
        <label class="input-label" for="account_username">
          <span>{{ $t('account.username') }}:</span>
          <input @keyup.enter="signIn" class="input-pane" v-model="username" id="account_username" type="text">
        </label>
      </div>
      <div class="input-frame">
        <label class="input-label" for="account_password">
          <span>{{ $t('account.password') }}:</span>
          <input @keyup.enter="signIn" class="input-pane" v-model="password" id="account_password" type="password">
        </label>
      </div>
      <div class="remember-me-checkbox">
        <check-box :checked="remember" @Check="Checked">
          <template v-slot:label>
            {{ $t('account.rememberMe') }}
          </template>
        </check-box>
      </div>
      <button @click="signIn" :class="passVerify?'sign-in sign-in-active':'sign-in'"></button>
      <div class="other-choose">
        <p @click="routerTo('/account/alter-password')">{{ $t('account.forget') }}</p>
        <p @click="routerTo('/account/sign-up')">{{ $t('account.toSignUp') }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import languageSelect from '@/components/language-select'
import CheckBox from '@/components/checkbox'

import logoImg from '@/assets/img/common/logo.png'
import langSvg from '@/assets/img/svg/language_green.svg';
import bkImgUrl from '@/assets/img/account/sign-in/background-img-ai.png';
import arrowSvg from '@/assets/img/svg/sign-in.svg';

export default {
  name: "index",
  data() {
    return {
      logoImg, langSvg, bkImgUrl, arrowSvg,
      username: '',
      password: '',
      passVerify: false,
      remember: false
    }
  },
  components: {
    languageSelect, CheckBox
  },
  methods: {
    Checked() {
      this.remember = !this.remember
    },
    routerTo(path) {
      this.$store.commit("needLoading", true)
      setTimeout(() => {
        this.$store.commit("needLoading", false)
        this.$router.push(path)
      }, 1500)
    },
    signIn() {
      if (this.passVerify) {
        this.api.userLogin(this.username, this.password).then(res => {
          if (res.data.code === 200) {
            let user = {
              username: this.username,
              password: this.password
            }
            if (this.remember) {
              localStorage.setItem("website-user", JSON.stringify(user));
            } else {
              localStorage.removeItem("website-user");
            }
            this.api.userInfo({
              method: 'get',
              url: '/user/info',
            }).then(rs => {
              if (rs.data.data) {
                sessionStorage.setItem("website-user-info", JSON.stringify(rs.data.data));
                this.routerTo('/account-information')
              } else {
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
      }
    }
  },
  updated() {
    this.passVerify = this.username !== '' && this.password !== '';
  },
  created() {
    let user = JSON.parse(localStorage.getItem("website-user"));
    if (user) {
      this.username = user.username;
      this.password = user.password;
      this.remember = true;
      this.passVerify = true;
    }
  }
}
</script>

<style scoped lang="scss">
#sign_in_framework {
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
    //height: 70%;
    padding: 2rem 0;
    background-color: rgba(240, 244, 240, .85);
    border-radius: 1rem;
    box-shadow: 0 0 15px rgba(66, 66, 66, .5);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    transform: scale(.95, .95);
    animation: 1.25s linear moveAnim;

    h1 {
      font-size: 2.5rem;
      text-transform: uppercase;
      letter-spacing: .2rem;
      margin: 0 0 1rem;
      text-shadow: 0 0 2px rgba(66, 66, 66, .35);
    }

    .input-frame {
      width: 70%;
      height: auto;
      margin: .5rem 0;
      border-radius: .5rem;
      border: 2px solid rgba(22, 22, 22, .8);
      background-color: rgba(255, 255, 255, .8);

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
      }

      span {
        margin-left: 4px;
      }
    }

    .remember-me-checkbox {
      margin: 1rem 0;
      width: 70%;
      //background-color: #008489;
    }

    .sign-in {
      width: 5rem;
      height: 5rem;
      margin: 1rem 0;
      cursor: pointer;
      box-sizing: border-box;
      border: 2.8px solid #312E2E;
      background-color: white;
      border-radius: .5rem;
      transition: .75s ease;
      background-position: center center;
      background-size: 60% 60%;
      background-repeat: no-repeat;
      background-image: url(../../../../assets/img/svg/sign-in.svg);
      opacity: 0.8;
    }

    .sign-in-active {
      opacity: 1;
      border: 2.8px solid #7aba01;
      background-color: #7aba01;
      background-image: url(../../../../assets/img/svg/sign-in-active.svg);
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
        transition: 1s ease;

        &:hover {
          color: #000000;
        }
      }
    }
  }
}

@media screen and (max-width: 1400px) {
  #sign_in_framework {

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
  #sign_in_framework {

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
  #sign_in_framework {

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

      .remember-me-checkbox {
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
  #sign_in_framework {

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

      .remember-me-checkbox {
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
