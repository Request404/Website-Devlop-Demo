<template>
  <div id="scroll_box">
    <div id="scroll_box_background"></div>
    <section ref="scroll_frame">
      <div id="record_change">{{ scrollIndex }}</div>
      <div id="scroll_forward" ref="scroll_forward">
        <slot name="forward_scroll">forward</slot>
      </div>
      <div id="scroll_left" ref="scroll_left">
        <slot name="left_scroll">left</slot>
      </div>
      <div id="scroll_right" ref="scroll_right">
        <slot name="right_scroll">right</slot>
      </div>
      <div id="scroll_back" ref="scroll_back">
        <slot name="back_scroll">back</slot>
      </div>
    </section>
    <div class="scroll_btn" id="left_scroll" @click="backScroll">
      <img src="../../assets/img/svg/bg-left-arrow.svg" alt="">
    </div>
    <div class="scroll_btn" id="right_scroll" @click="forwardScroll">
      <img src="../../assets/img/svg/bg-right-arrow.svg" alt="">
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      scrollIndex: 0,
      isClick: true,
      scrollTime: null
    }
  },
  methods: {
    backScroll() {
      if (this.isClick) {
        clearInterval(this.scrollTime)
        this.isClick = false
        this.scrollIndex--
        this.scrollControl()
        setTimeout(() => {
          this.isClick = true
          this.scrollTime = setInterval(() => {
            this.scrollIndex++;
            this.scrollControl()
            if (this.scrollIndex >= 10000) {
              this.scrollIndex = 0;
            }
            console.log(this.scrollIndex);
          }, 6000)
        }, 1300)
        // return this.scrollIndex
      }
    },
    forwardScroll() {
      clearInterval(this.scrollTime)
      if (this.isClick) {
        this.isClick = false
        this.scrollIndex++
        this.scrollControl()
        setTimeout(() => {
          this.isClick = true
          this.scrollTime = setInterval(() => {
            this.scrollIndex++;
            this.scrollControl()
            if (this.scrollIndex >= 10000) {
              this.scrollIndex = 0;
            }
            console.log(this.scrollIndex);
          }, 6000)
        }, 1300)
        // return this.scrollIndex
      }
    },
    scrollControl() {
      let scrollDeg = this.scrollIndex * 90
      this.$refs.scroll_frame.style.transform = 'scale(0.5,0.5) rotateY(' + scrollDeg + 'deg)'
      setTimeout(() => {
        this.$refs.scroll_frame.style.transform = 'scale(1,1) rotateY(' + scrollDeg + 'deg)'
      }, 1200)
    }
  },
  computed: {},
  beforeUpdate() {
    if(this.$refs.scroll_frame){
      let scrollWidth = this.$refs.scroll_frame.clientWidth / 2;
      this.$refs.scroll_forward.style.transform = 'rotateY(0deg) translateZ(' + scrollWidth + 'px)'
      this.$refs.scroll_left.style.transform = 'rotateY(-90deg) translateZ(' + scrollWidth + 'px)'
      this.$refs.scroll_right.style.transform = 'rotateY(90deg) translateZ(' + scrollWidth + 'px)'
      this.$refs.scroll_back.style.transform = 'rotateY(180deg) translateZ(' + scrollWidth + 'px)'
    }
  },
  mounted() {
    this.scrollTime = setInterval(() => {
      this.scrollIndex++;
      this.scrollControl()
      if (this.scrollIndex >= 10000) {
        this.scrollIndex = 0;
      }
    }, 6000)
  }
}
</script>

<style scoped>
#scroll_box {
  width: 80%;
  min-width: 920px;
  margin: auto;
  padding: 1.2rem 0;
  height: 600px;
  position: relative;
  display: flex;
  align-items: center;
  transform-style: preserve-3d;
  cursor: pointer;
}

/*#scroll_box_background{*/
/*	width: 100%;*/
/*	height: 100%;*/
/*	background-color: #7efff5;*/
/*	z-index: 0;*/
/*	border-radius: 0 0 40% 40%;*/
/*}*/

#scroll_box > section {
  width: 100%;
  height: 100%;
  display: flex;
  margin: auto;
  position: relative;
  transform-style: preserve-3d;
  transition: 1.2s ease;
}

#scroll_box > section > div {
  width: 100%;
  height: 100%;
  position: absolute;
  /*border: 2px solid #7aba01;*/
  box-shadow: 0 0 15px rgba(66, 66, 66, .38);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  /*background-color: #7aba01;*/
}

#scroll_forward {
  z-index: 4;
}

#scroll_left {
  z-index: 3;
}

#scroll_right {
  z-index: 2;
}

#scroll_back {
  z-index: 1;
}

#scroll_box > .scroll_btn {
  user-select: none;
  width: 3.8rem;
  height: 3.8rem;
  background-color: rgba(255, 255, 255, 1);
  box-shadow: 0 0 5px rgba(66, 66, 66, .38);
  border: .15rem solid #7aba01;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  transition: .1s ease;
  position: absolute;
  z-index: 10;
  transform: translateZ(85rem);
}

#scroll_box > .scroll_btn img {
  border-radius: 50%;
  /*border: .1rem solid #7aba01;*/
  width: 2.6rem;
  height: 2.6rem;
}

#left_scroll {
  left: -2rem;
  z-index: 5;
}

#right_scroll {
  right: -2rem;
  z-index: 4;
}

#left_scroll:hover {
  width: 3.9rem;
  height: 3.9rem;
  z-index: 3;
}

#right_scroll:hover {
  width: 3.9rem;
  height: 3.9rem;
  z-index: 2;
}

#record_change {
  display: none;
}

@media screen  and (max-width: 120rem) {
  #scroll_box {
    height: 500px;
  }
}

@media screen and (min-width: 120rem) {
  #scroll_box {
    height: 600px;
  }
}
</style>
