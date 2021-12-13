<template>
  <div class="req-checkbox" >
    <div @click="isCheck" :class="checked?'req-checkbox-box req-checkbox-box-active':'req-checkbox-box'"></div>
    <div @click="isCheck" :class="checked?'req-checkbox-label req-checkbox-label-active':'req-checkbox-label'">
      <slot name="label">
        选择
      </slot>
    </div>
  </div>
</template>

<script>
import CheckSvg from '@/assets/img/svg/Check.svg'

export default {
  name: "index",
  props: {
    checked: {
      type: Boolean,
      default() {
        return false
      },
      required: true
    }
  },
  data() {
    return {
      CheckSvg
    }
  },
  methods: {
    isCheck(){
      // this.checked = !this.checked;
      this.$emit('Check', this.checked);
    }
  }
}
</script>

<style scoped lang="scss">
.req-checkbox {
  width: auto;
  display: flex;
  align-items: center;
  user-select: none;
  //background-color: #008489;

  .req-checkbox-box {
    width: 1.5rem;
    height: 1.5rem;
    border-radius: 5px;
    border: 2px solid rgba(22, 22, 22, .85);
    cursor: pointer;
    background-size: 100% 100%;
    background-position: center center;
    transition: .75s ease;
    background-image: url('../../assets/img/svg/Check.svg');
  }

  .req-checkbox-box-active {
    border: 2px solid #7aba01;
    background-image: url('../../assets/img/svg/Check-active.svg');
  }

  .req-checkbox-label {
    margin-left: .5rem;
    font-size: 1rem;
    cursor: pointer;
    padding-top: 1px;
    transition: .75s ease;
  }

  .req-checkbox-label-active {
    color: #7aba01;
  }
}


@media screen and (max-width: 600px) {
  .req-checkbox {

    .req-checkbox-box {
      width: 1.35rem;
      height: 1.35rem;
    }
  }

}

@media screen and (max-width: 600px) {
  .req-checkbox {

    .req-checkbox-box {
      width: 1rem;
      height: 1rem;
    }

    .req-checkbox-label {
      font-size: .9rem;
    }
  }

}
</style>
