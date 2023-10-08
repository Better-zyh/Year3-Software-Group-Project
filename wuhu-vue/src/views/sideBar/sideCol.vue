<template>
  <section>
    <!--先检查是否登录，如果登陆了，则可以发帖-->
    <PostIcon />
    <!--一个空div，用margin-top使得上下分隔-->
    <div style="margin-top: 20px"></div>
    <!--广告/推广-->
    <PromotionCard />
  </section>
</template>

<script>
import PromotionCard from '../sideBar/adCard'
import PostIcon from '../sideBar/postCard'
export default {
  name: 'sideBar',
  components: { PostIcon, PromotionCard }
}
</script>

<style scoped>
</style>