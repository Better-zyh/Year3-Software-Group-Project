<template>
  <div>
    <span>code</span>
    <el-input type="code" v-model="code" autocomplete="off"></el-input>
    <el-button type="primary" @click="submit()">Submit</el-button>
  </div>

</template>

<script>
import { sendcode} from "../api/post";
export default {
  name: "identifying_code",
  data() {
    return{

        code: '',
    }
  },
  methods:{
    submit(){
      alert(this.code);
      sendcode(this.code).then((response) => {
        const {code,data} = response;
        if(code == 200){
          alert("success!!");
          this.$router.push({path: "../Profile"});
        }
      })
    }
    ,

  },
  created() {
    //页面加载时就从本地通过localstorage获取存储的token值
    this.token = localStorage.getItem('token');

  },
}
</script>

<style scoped>

</style>