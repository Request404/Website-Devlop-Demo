<template>
  <div id="nav_bar_frame">
    <div id="nav_bar_container">
      <h1>—— {{ word[Math.round(Math.random()*word.length)] }}</h1>
      <div id="nav_bar_avatar">
        <Dropdown>
          <div class="avatar">
            <img  :src="userInfo.headPortrait===null||''?defaultAvatar:userInfo.headPortrait" alt="">
          </div>
          <DropdownMenu  slot="list">
            <DropdownItem>{{ userInfo.username }}（{{ userInfo.role }}）</DropdownItem>
            <DropdownItem divided ><span @click="logout()">退出登录</span></DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import defaultAvatar from '@/assets/img/home/avatar.gif';

export default {
  name: "index",
  data() {
    return {
      userInfo: null,
      defaultAvatar,
      word: [
          '有些路，走下去会很苦很累，但是不走会后悔。',
          '就算全世界都否定你，你也要相信你自己。',
          '每天多一点点的努力，不为别的，只为了日后能够多一些选择。',
          '花一些时间，总会看清一些事。用一些事情，总会看清一些人。'
      ]
    }
  },
  methods: {
    logout() {
      this.$store.commit('needLoading', true)
      setTimeout(() => {
        sessionStorage.clear();
        this.$router.push('/administration/login')
        this.$message({
          message: '注销成功！',
          type: 'success'
        });
        this.$store.commit('needLoading', false)
      }, 1500)
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem("website-user-info"))
  }
}
</script>

<style scoped lang="scss">
#nav_bar_frame {
  width: 100%;
  height: 10%;
  background: #5A6275;
  box-shadow: 0 0 10px rgba(22, 22, 22, .38);

  #nav_bar_container {
    width: 100%;
    height: 100%;
    position: relative;

    h1{
      height: 100%;
      display: flex;
      padding-left: 2rem;
      //justify-content: center;
      align-items: center;
      color: #fff;
      font-size: 1.35rem;
    }

    #nav_bar_avatar {
      position: absolute;
      right: 45px;
      top: 5px;
    }
  }
}

.avatar {
  width: 4rem;
  height: 4rem;
  margin: .3rem 0 0 0;
  border-radius: 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.avatar>img{
  width: 100%;
  height: auto;
}
</style>
