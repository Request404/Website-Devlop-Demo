<template>
  <div id="select_lang">
    <figure @mouseover="langSelectIn" @mouseleave="langSelectOut" id="select_lang_icon">
      <slot name="icon">
        <img :src="langSvg" alt="">
      </slot>
    </figure>
    <div id="arrow" v-show="isShow"></div>
    <ul id="lang_items" @mouseover="langSelectIn" @mouseleave="langSelectOut" v-show="isShow">
      <li class="lang-item" v-for="(item,index) in lang"
          :key="index"
          :style="locale===item.type ? 'color: #7aba01': '' "
          @click="langChange(item.type)">{{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script>
import langSvg from '../../assets/img/svg/language.svg'

export default {
  name: "index",
  data() {
    return {
      langSvg: langSvg,
      locale: null,
      lang: [{name: '简体中文', type: 'zh'}, {name: 'English', type: 'en'}],
      isShow: false,
      times: null
    }
  },
  methods: {
    langSelectIn() {
      clearTimeout(this.times)
      this.isShow = true;
    },
    langSelectOut() {
      this.times = setTimeout(() => {
        this.isShow = false;
      }, 1000)
    },
    langChange(locale) {
      this.locale = locale;
      localStorage.setItem("locale", locale);
      location.reload();
    }
  },
  created() {
    this.locale = localStorage.getItem("locale") || 'zh';

  }
}
</script>

<style scoped lang="scss">
@import "../../assets/css/common";

#select_lang {
  width: 5rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  //background-color: #409EFF;

  #select_lang_icon {
    width: 40%;
    height: auto;
    cursor: pointer;
    line-height: 0;

    img {
      width: 100%;
      height: auto;
    }
  }

  #arrow {
    position: absolute;
    top: 1.9rem;
    z-index: 100;
    border: 8px solid transparent;
    border-bottom-color: rgba(255, 255, 255, .9)
  }

  #lang_items {
    width: 100%;
    position: absolute;
    top: calc(1.9rem + 8px * 2);
    z-index: 100;
    list-style: none;
    border-radius: 5px;
    overflow: hidden;
    border: 1px solid rgba(222, 222, 222, 1);

    .lang-item {
      padding: .3rem .5rem;
      background-color: rgba(255, 255, 255, .9);
      font-weight: bold;
      color: rgba(100, 100, 100, .95);
      border-top: 1px solid rgba(222, 222, 222, 1);
      cursor: pointer;
      transition: .75s ease;

      &:nth-child(1) {
        border-top: none;
      }

      &:hover {
        background-color: rgba(255, 255, 255, 1);
      }
    }

  }
}

@media screen and (max-width: 600px) {
  #select_lang {
    margin-right: 2px;
    width: 5rem;
    justify-content: right;
    align-items: center;
    font-size: .8rem;

    #select_lang_icon {
      margin-right: 5px;
    }

    #arrow {
      margin-right: calc(2rem / 4 + 5px);
    }

  }
}


@media screen and (max-width: 480px) {

  #select_lang {
    margin-right: 2px;
    width: 5rem;
    align-items: flex-end;
    //font-size: .85rem;

    #select_lang_icon {
      margin-right: 5px;
    }

    #arrow {
      margin-right: calc(2rem / 4 + 5px);
    }
  }
}

</style>
