<template>
  <div id="search_bar">
    <div id="search_frame">
      <input v-model="keyword" :class="onFocus?'search-input search-input-active':'search-input'" type="text"
             :placeholder="$t('nav.search')">
      <div @click="touch" :class="onFocus?'search-button search-button-active':'search-button'"
           @mouseenter="onFocus = true"
           @mouseleave="onFocus = false"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      onFocus: false,
      keyword: '',
    }
  },
  watch: {
    keyword: function (newVal) {
      if(newVal.trim()===''){
        this.$emit('onClick', this.keyword);
      }
    }
  },
  methods: {
    touch() {
      this.$emit('onClick', this.keyword);
    }
  }
}
</script>

<style scoped lang="scss">
#search_bar {
  width: 25rem;
  height: 100%;
  margin: 0 5rem;
  display: flex;
  justify-content: center;
  align-items: center;

  #search_frame {
    width: 90%;
    height: 3rem;
    //background: #7aba01;
    display: flex;
    justify-content: center;
    align-items: center;

    .search-input {
      width: calc(100% - 2rem);
      height: 100%;
      outline: none;
      padding: .5rem .8rem;
      background-color: rgba(0, 0, 0, 0);
      font-size: 1.2rem;
      color: #7aba01;
      border: 2px solid #7aba01;
      border-right: none;
      border-radius: 8px 0 0 8px;
      transition: .35s ease;
      opacity: 0.8;

      &::placeholder {
        color: rgba(22, 22, 22, .65);
      }
    }

    .search-button {
      width: 3rem;
      height: 3rem;
      border: 2px solid #7aba01;
      background-color: #7aba01;
      background-image: url("../../assets/img/svg/search_active.svg");
      background-repeat: no-repeat;
      background-position: center center;
      background-size: 66% 66%;
      cursor: pointer;
      border-radius: 0 8px 8px 0;
      transition: .75s ease;
      opacity: 0.8;

      &:hover {
        opacity: 1;
      }
    }
  }
}

@media screen and (max-width: 1400px) {
  #search_bar {
    width: 25rem;
    margin: 0 2rem;
  }
}

@media screen and (max-width: 1024px) {
  #search_bar {
    width: 20rem;
    height: 100%;
    margin: 0 2rem;
    display: flex;
    justify-content: center;
    align-items: center;

    #search_frame {
      width: 90%;
      height: 3rem;
      //background: #7aba01;
      display: flex;
      justify-content: center;
      align-items: center;

      .search-input {
        width: calc(100% - 2rem);
        height: 100%;
        outline: none;
        padding: .5rem .8rem;
        background-color: rgba(0, 0, 0, 0);
        font-size: 1.2rem;
        color: #7aba01;
        border: 2px solid #7aba01;
        border-right: none;
        border-radius: 8px 0 0 8px;
        transition: .35s ease;
        opacity: 0.8;

        &::placeholder {
          color: rgba(22, 22, 22, .65);
        }
      }

      .search-button {
        width: 3rem;
        height: 3rem;
        border: 2px solid #7aba01;
        background-color: #7aba01;
        background-image: url("../../assets/img/svg/search_active.svg");
        background-repeat: no-repeat;
        background-position: center center;
        background-size: 66% 66%;
        cursor: pointer;
        border-radius: 0 8px 8px 0;
        transition: .75s ease;
        opacity: 0.8;

        &:hover {
          opacity: 1;
        }
      }
    }
  }
}

//
//@media screen and (max-width: 600px) {
//  #search_bar {
//    width: 25rem;
//    height: 100%;
//    margin: 0 5rem;
//    display: flex;
//    justify-content: center;
//    align-items: center;
//
//    #search_frame {
//      width: 90%;
//      height: 3rem;
//      //background: #7aba01;
//      display: flex;
//      justify-content: center;
//      align-items: center;
//
//      .search-input {
//        width: calc(100% - 2rem);
//        height: 100%;
//        outline: none;
//        padding: .5rem .8rem;
//        background-color: rgba(0, 0, 0, 0);
//        font-size: 1.2rem;
//        color: #7aba01;
//        border: 2px solid #7aba01;
//        border-right: none;
//        border-radius: 8px 0 0 8px;
//        transition: .35s ease;
//        opacity: 0.8;
//
//        &::placeholder {
//          color: rgba(22, 22, 22, .65);
//        }
//      }
//
//      .search-button {
//        width: 3rem;
//        height: 3rem;
//        border: 2px solid #7aba01;
//        background-color: #7aba01;
//        background-image: url("../../assets/img/svg/search_active.svg");
//        background-repeat: no-repeat;
//        background-position: center center;
//        background-size: 66% 66%;
//        cursor: pointer;
//        border-radius: 0 8px 8px 0;
//        transition: .75s ease;
//        opacity: 0.8;
//
//        &:hover {
//          opacity: 1;
//        }
//      }
//    }
//  }
//}
//
//@media screen and (max-width: 350px) {
//  #search_bar {
//    width: 25rem;
//    height: 100%;
//    margin: 0 5rem;
//    display: flex;
//    justify-content: center;
//    align-items: center;
//
//    #search_frame {
//      width: 90%;
//      height: 3rem;
//      //background: #7aba01;
//      display: flex;
//      justify-content: center;
//      align-items: center;
//
//      .search-input {
//        width: calc(100% - 2rem);
//        height: 100%;
//        outline: none;
//        padding: .5rem .8rem;
//        background-color: rgba(0, 0, 0, 0);
//        font-size: 1.2rem;
//        color: #7aba01;
//        border: 2px solid #7aba01;
//        border-right: none;
//        border-radius: 8px 0 0 8px;
//        transition: .35s ease;
//        opacity: 0.8;
//
//        &::placeholder {
//          color: rgba(22, 22, 22, .65);
//        }
//      }
//
//      .search-button {
//        width: 3rem;
//        height: 3rem;
//        border: 2px solid #7aba01;
//        background-color: #7aba01;
//        background-image: url("../../assets/img/svg/search_active.svg");
//        background-repeat: no-repeat;
//        background-position: center center;
//        background-size: 66% 66%;
//        cursor: pointer;
//        border-radius: 0 8px 8px 0;
//        transition: .75s ease;
//        opacity: 0.8;
//
//        &:hover {
//          opacity: 1;
//        }
//      }
//    }
//  }
//}
</style>
