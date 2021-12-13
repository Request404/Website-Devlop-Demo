<template>
  <div ref="index_page" :class="getStart?'index-page':''" id="index_page">
    <header>
      <h1 id="title_logo">
        <img :src="request404" alt="">
      </h1>
      <div id="lang_change">
        <language-select/>
      </div>
    </header>
    <div id="get_started">
      <div @mouseover="rockRoll = true"
           @mouseleave="rockRoll = false"
           @click="getStarting"
           id="get_started_button">
        {{ $t("index.getStarted") }}
      </div>
      <div id="roll_rock">
        <img :class="rockRoll?'outside-svg':''" id="outside_svg" src="../../assets/img/svg/test01.svg" alt="">
        <img :class="rockRoll?'inside-svg':''" id="inside_svg" src="../../assets/img/svg/test02.svg" alt="">
      </div>
    </div>
    <footer>
      <div>
        <span> {{ $t("index.email") }}：<a href="http://mail.qq.com">Request404@qq.com</a></span>
        <span> {{ $t("index.licenses") }}：<a href="https://www.apache.org/licenses/LICENSE-2.0">Apache 2.0</a></span>
        <span><a href="#"> {{ $t("index.support") }}!</a></span>
      </div>
      <div>
        COPYRIGHT © 2021 Request 404. ALL RIGHTS RESERVED.
      </div>
    </footer>
  </div>
</template>

<script>
import languageSelect from '@/components/language-select'

import request404 from '@/assets/img/index/request404.png';
import indexImg01 from '@/assets/img/index/index01.jpg';
import indexImg02 from '@/assets/img/index/index02.jpg';
import indexImg03 from '@/assets/img/index/music.jpg';

export default {
  name: "index",
  data() {
    return {
      request404: request404,
      indexImg: [indexImg01, indexImg02, indexImg03],
      rockRoll: false,
      getStart: false
    }
  },
  components: {
    languageSelect
  },
  methods: {
    getStarting() {
      this.getStart = true
      setTimeout(() =>{
        this.$router.push('/home')
      },1000)
    }
  },
  mounted() {
    let index = 1
    setInterval(() => {
      this.$refs.index_page.style.backgroundImage = 'url("' + this.indexImg[index] + '")';
      index++;
      if (index === this.indexImg.length) {
        index = 0
      }
    }, 10000)
  }
}
</script>

<style scoped lang="scss">
#index_page {
  width: 100%;
  height: 100%;
  background-image: url('../../assets/img/index/index01.jpg');
  background-size: 100% auto;
  background-position: center center;
  animation: 10s bmgAnim linear infinite;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  user-select: none;

  header {
    width: 100%;
    display: flex;
    justify-content: space-between;

    #title_logo {
      width: 28%;
      height: auto;
      position: relative;
      top: -15%;

      img {
        width: 100%;
        height: auto;
      }
    }

    #lang_change {
      margin-top: 1.5rem;
    }
  }

  #get_started {
    //width: 20%;
    height: auto;
    position: absolute;
    left: 50%;
    top: 55%;
    transform: translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    animation: 1.2s getStartedAnim linear;

    #get_started_button {
      //width: 100%;
      padding: .5rem 1rem;
      text-align: center;
      font-size: 2rem;
      border-radius: 2rem;
      border: .25rem solid white;
      text-transform: uppercase;
      user-select: none;
      cursor: pointer;
      color: white;
      opacity: .95;
      transition: .75s ease;

      &:hover {
        opacity: 1;
        box-shadow: 0 0 1rem rgba(255, 255, 222, .75);
        text-shadow: 0 0 8px rgba(255, 255, 222, .75);
      }
    }

    #roll_rock {
      width: 100%;
      height: 5rem;
      position: relative;

      img {
        position: absolute;
        //transform: translate(-50%, -50%)
      }

      #outside_svg {
        top: 10%;
        left: 28.5%;
        height: 100%;
        z-index: 2;
        transform: rotateZ(0deg) scale(.8, .8);
      }

      .outside-svg {
        animation: 2.5s outsideSvg infinite;
      }

      #inside_svg {
        top: 30%;
        left: 36%;
        height: 60%;
        z-index: 1;
        transform: scale(1.6, 1.6);
      }

      .inside-svg {
        animation: 2.5s insideSvg infinite;
      }
    }
  }

  footer {
    width: 100%;
    background-color: rgba(0, 0, 0, .2);
    padding: .2rem 0;
    position: absolute;
    bottom: 0;
    color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    span {
      margin: .5rem;
    }

    a {
      color: white;
      transition: .75s ease;

      &:hover {
        text-shadow: 0 0 5px rgba(100, 100, 100, .85);
      }
    }
  }
}

.index-page {
  animation: 1s ease-in srollUp !important;
}

@media screen and (max-width: 1050px) {
  #index_page {
    background-size: auto 100%;
    animation: 10s otherBmgAnim linear infinite;

    header {
      #title_logo {
        width: 50%;
        height: auto;
        position: relative;
        top: -10%;
      }

      #lang_change {
        margin-top: 1rem;
      }
    }

  }
}

@media screen and (max-width: 600px) {
  #index_page {
    background-size: auto 100%;
    animation: 10s otherBmgAnim linear infinite;

    header {
      #title_logo {
        width: 50%;
        height: auto;
        position: relative;
        top: -10%;
      }

      #lang_change {
        margin-top: .8rem;
      }


    }

    #get_started {
      top: 50%;
      animation: 1.2s minGetStartedAnim linear;

      #get_started_button {
        font-size: 1.56rem;
      }

      #roll_rock {

        #outside_svg {
          top: 10%;
          left: 23.5%;
          height: 100%;
        }

        #inside_svg {
          top: 30%;
          left: 34.2%;
          height: 60%;
        }
      }
    }

    footer {
      font-size: .65rem;

      div {
        text-align: center;

        span {
          margin: .15rem;
          text-align: center;
        }
      }

    }
  }
}

@media screen and (max-width: 480px) {
  #index_page {
    background-size: auto 100%;
    animation: 10s otherBmgAnim linear infinite;

    header {
      #title_logo {
        width: 50%;
        height: auto;
        position: relative;
        top: 0;
      }

      #lang_change {
        margin-top: .6rem;
      }
    }

    #get_started {
      top: 50%;
      animation: 1.2s minGetStartedAnim linear;

      #get_started_button {
        font-size: 1.35rem;
      }
    }

    footer {
      font-size: .65rem;

      div {
        text-align: center;

        span {
          margin: .15rem;
        }
      }
    }
  }
}

@keyframes srollUp {
  from {
    top: 0;
  }
  to {
    top: -120%;
  }
}

@keyframes bmgAnim {
  0% {
    background-size: 100% auto;
  }
  80% {
    background-size: 105% auto;
  }
  100% {
    background-size: 100% auto;
  }
}

@keyframes otherBmgAnim {
  0% {
    background-size: auto 100%;
  }
  80% {
    background-size: auto 105%;
  }
  100% {
    background-size: auto 100%;
  }
}

@keyframes getStartedAnim {
  0% {
    top: 58%;
    opacity: 0;
  }
  60% {
    top: 54.5%;
  }
  100% {
    top: 55%;
    opacity: 1;
  }
}

@keyframes minGetStartedAnim {
  0% {
    top: 58%;
    opacity: 0;
  }
  60% {
    top: 49.5%;
  }
  100% {
    top: 50%;
    opacity: 1;
  }
}

@keyframes outsideSvg {
  0% {
    transform: rotateZ(0deg) scale(.8, .8);
  }
  50% {
    transform: rotateZ(180deg) scale(.95, .95);
  }
  100% {
    transform: rotateZ(360deg) scale(.8, .8);;
  }
}

@keyframes insideSvg {
  0% {
    transform: scale(1.6, 1.6);
  }
  50% {
    transform: scale(.6, .6);
  }
  100% {
    transform: scale(1.6, 1.6);
  }
}
</style>
