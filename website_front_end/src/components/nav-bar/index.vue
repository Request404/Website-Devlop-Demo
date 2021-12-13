<template>
  <div id="nav_bar_frame">
    <h1 id="logo1">
      <img style="cursor: pointer" @click="routerTo('/home')" :src="logo" alt="">
    </h1>
    <div id="nav">
      <ul id="nav_list">
        <li @click="routerTo('/movies')" class="nav-item">{{ $t('nav.movies') }}</li>
        <li @click="routerTo('/musics')" class="nav-item">{{ $t('nav.musics') }}</li>
        <li @click="routerTo('/books')" class="nav-item">{{ $t('nav.books') }}</li>
      </ul>
      <div id="phone_nav_list">
        <figure ref="nav_list_icon" @click="getNavItem">
          <img :src="websiteUserInfo?websiteUserInfo.headPortrait:userSvg" alt="">
        </figure>
        <ul ref="phone_nav_list_ul"
            :class="navListShowClass">
          <li class="nav-item">{{ $t('nav.movies') }}</li>
          <li class="nav-item">{{ $t('nav.musics') }}</li>
          <li class="nav-item">{{ $t('nav.books') }}</li>
        </ul>
      </div>
    </div>
    <div id="search_bar">
      <slot name="search">
      </slot>
    </div>
    <div id="nav_right_side">
      <div id="language_select">
        <language-select>
          <template v-slot:icon>
            <img :src="langSvg" alt="">
          </template>
        </language-select>
      </div>
      <div @click="userInfo" id="user_info">
        <figure>
          <img :src="websiteUserInfo?websiteUserInfo.headPortrait:userSvg" alt="">
        </figure>
      </div>
    </div>
  </div>
</template>

<script>
import languageSelect from '@/components/language-select';

import logo from '@/assets/img/common/logo.jpg';
import langSvg from '@/assets/img/svg/language_black.svg';
import navList from '@/assets/img/svg/nav_list.svg'
import avatar from '@/assets/img/home/avatar.gif';
import userSvg from '@/assets/img/common/user_.svg'

export default {
  name: "index",
  data() {
    return {
      logo, langSvg, avatar, navList, userSvg,
      navListShow: false,
      navListShowClass: 'phone-nav-list-ul phone-nav-ul-init',
      websiteUserInfo: null,
    }
  },
  methods: {
    routerTo(path) {
      this.$store.commit("needLoading", true)
      setTimeout(() => {
        this.$store.commit("needLoading", false)
        this.$router.push(path)
      }, 1500)
    },
    getNavItem() {
      if (!this.navListShow) {
        this.$refs.nav_list_icon.style.height = '2.2rem';
        // this.$refs.phone_nav_list_ul.style.animation = '1s linear phoneNavAnim';
        this.navListShowClass = 'phone-nav-list-ul phone-nav-ul-active'
        this.navListShow = true;
      } else {
        this.$refs.nav_list_icon.style.height = '2rem'
        this.navListShowClass = 'phone-nav-list-ul phone-nav-ul-none'
        this.navListShow = false;
      }
    },
    userInfo() {
      if(sessionStorage.getItem("website-user-info")){
        this.$router.push('/account-information')
      }else{
        this.$router.push('/account')
      }
    }
  },
  components: {languageSelect},
  created() {
    if (sessionStorage.getItem('website-user-info')) {
      this.websiteUserInfo = JSON.parse(sessionStorage.getItem('website-user-info'));
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/css/common";

#nav_bar_frame {
  width: 100%;
  height: 100%;
  display: flex;
  //justify-content: center;
  align-items: center;
  position: relative;
  //z-index: 100;
  background-color: white;

  #logo1 {
    width: auto;
    height: 100%;
    margin: 0 2rem;
    display: flex;
    justify-content: center;
    align-items: center;
    //align-self: flex-start;

    img {
      width: auto;
      height: 100%;
    }
  }

  #nav {
    height: 100%;

    #nav_list {
      height: 100%;
      list-style: none;
      display: flex;
      justify-content: center;
      align-items: center;

      .nav-item {
        //font-family: "MS Outlook";
        font-size: 1.5rem;
        color: rgba(22, 22, 22, .95);
        text-shadow: 0 0 3px rgba(166, 122, 166, .35);
        font-weight: bold;
        padding: 0 1rem;
        text-transform: uppercase;
        cursor: pointer;
        transition: .75s ease;
        opacity: .8;

        &:hover {
          opacity: 1;
          text-shadow: 0 0 3px rgba(166, 122, 166, .45);
        }
      }
    }

    #phone_nav_list {
      display: none;
    }
  }

  #search_bar {
    height: 100%;
  }

  #nav_right_side {
    height: 100%;
    position: absolute;
    right: 2%;
    display: flex;
    justify-content: center;
    align-items: center;

    #language_select {
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    #user_info {
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;

      figure {
        width: 4.5rem;
        height: 4.5rem;
        overflow: hidden;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 50%;
        box-shadow: 0 0 5px rgba(66, 66, 66, .35);

        img {
          width: 100%;
          height: auto;
          //border: 2px solid rgba(66, 66, 66, .85);
          cursor: pointer;
        }
      }
    }
  }
}

@media screen and (max-width: 1024px) {
  #nav_bar_frame {
    width: 100%;
    height: 100%;

    #logo1 {
      width: auto;
      height: 100%;
      margin: 0 1rem;


      img {
        width: auto;
        height: 75%;
      }
    }

    #nav {

      #nav_list {

        .nav-item {
          //font-family: "MS Outlook";
          font-size: 1.55rem;
          color: rgba(22, 22, 22, .95);
          font-weight: bold;
        }
      }
    }

    #nav_right_side {

      #user_info {

        figure {
          height: auto;
          display: flex;
          justify-content: center;
          align-items: center;

          img {
            width: 4.8rem;
            height: 4.8rem;
            border-radius: 50%;
            box-shadow: 0 0 5px rgba(66, 66, 66, .35);
            cursor: pointer;
          }
        }
      }
    }
  }
}

@media screen and (max-width: 878px) {
  #nav_bar_frame {

    #search_bar {
      display: none;
    }
  }
}

@media screen and (max-width: 600px) {
  #nav_bar_frame {
    width: 100%;
    height: 100%;
    justify-content: space-between;
    box-shadow: 0 0 3px rgba(22, 22, 22, .35);

    #logo1 {
      width: auto;
      height: 100%;
      margin: 0 1rem;
      order: 2;


      img {
        width: auto;
        height: 75%;
      }
    }

    #nav {
      order: 1;

      #nav_list {
        display: none;
      }

      #phone_nav_list {
        width: 7rem;
        height: 100%;
        display: flex;
        align-items: center;
        left: 2%;

        figure {
          height: 2rem;
          padding: .2rem;
          transition: .75s ease;

          img {
            width: auto;
            height: 100%;
          }

          //&:hover {
          //  height: 2.2rem;
          //}
        }

        .phone-nav-list-ul {
          width: 100%;
          position: absolute;
          list-style: none;

          .nav-item {
            width: 100%;
            font-size: 1.1rem;
            padding: .2rem .5rem;
            font-weight: 900;
            color: #fff;
            text-shadow: 0 0 3px rgba(22, 22, 22, .35);
            background-color: rgba(22, 22, 22, .35);
            border-bottom: 1px solid rgba(66, 66, 66, .35);
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }

        .phone-nav-ul-init {
          top: -100%;
          z-index: -10;
        }

        .phone-nav-ul-active {
          top: 100%;
          z-index: 50;
          animation: .75s linear phoneNavAnimActive;
        }

        .phone-nav-ul-none {
          top: -100%;
          z-index: -10;
          animation: .75s linear phoneNavAnimNone;
        }
      }
    }

    #nav_right_side {
      order: 3;
      position: relative;
      //right: 0;

      #user_info {

        figure {
          height: auto;
          display: flex;
          justify-content: center;
          align-items: center;

          img {
            width: 2rem;
            height: 2rem;
            border-radius: 50%;
            //border: 2px solid rgba(66, 66, 66, .85);
            box-shadow: 0 0 5px rgba(66, 66, 66, .35);
            cursor: pointer;
          }
        }
      }
    }
  }
}

@media screen and (max-width: 480px) {
  #home_page_frame {
    //background-color: saddlebrown;
  }
}

@media screen and (max-width: 300px) {
  #home_page_frame {

    #logo1 {
      display: none;
    }

  }
}

@keyframes phoneNavAnimActive {
  0% {
    top: -100%;
    z-index: -10;
  }
  99% {
    z-index: -10;
  }
  100% {
    top: 100%;
    z-index: 50;
  }
}

@keyframes phoneNavAnimNone {
  0% {
    top: 100%;
    z-index: 50;
  }
  1% {
    z-index: -10;
  }
  100% {
    top: -100%;
    z-index: -10;
  }
}
</style>
