<template>
  <div id="copyright_frame">
    <div id="copyright_container">
      <p>
        Request404 网站管理系统
        <br>
        <a href="">Apache license 2.0</a>
      </p>
      <p>
        Copyright ©2021 reqeuest404.com All rights reserved.
        <Icon size="18" color="rgba(180,180,180,.95)" type="ios-log-out"/>
      </p>
      <p>

      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "copyright"
}
</script>

<style scoped lang="scss">
#copyright_frame {
  width: 100%;
  padding: 12px 0;
  height: auto;
  display: flex;
  justify-content: center;
  align-items: center;

  #copyright_container {
    width: 88%;

    p {
      color: rgba(180, 180, 180, .85);
      cursor: pointer;
      font-weight: 100;
      font-size: .85rem;

      a {
        color: rgba(220, 220, 220, .85);
        transition: .5s ease;

        &:hover {
          color: rgba(240, 240, 240, 1);
        }
      }
    }
  }
}
</style>
