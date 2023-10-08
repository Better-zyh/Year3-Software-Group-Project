<template>
  <div>
    <div>
      <el-card class="box-card" shadow="never">
        <div class="buttons">
          <b-button size="is-large" style="margin-right: auto;margin-left: auto" @click="toPostCreate">Click to post</b-button>
        </div>
      </el-card>
    </div>
    <div >
      <el-dialog title="Post" :visible.sync="dialogVisible" width="600px" center>
        <el-card
                class="box-card"
                shadow="never"
        >
          <div
                  slot="header"
                  class="clearfix"
          >
            <span><i class="fa fa fa-book"> Topic / Topic title </i></span>
          </div>
          <div>
            <el-form
                    ref="ruleForm"
                    :model="ruleForm"
                    :rules="rules"
                    class="demo-ruleForm"
            >
              <el-form-item prop="title">
                <el-input
                        v-model="ruleForm.title"
                        placeholder="Input the title"
                />
              </el-form-item>
              <el-form-item prop="content">
                <el-input
                        type="textarea"
                        :rows="4"
                        placeholder="Input the content"
                        v-model="ruleForm.content">
                </el-input>
              </el-form-item>
              <b-taginput
                      v-model="ruleForm.tags"
                      class="my-3"
                      maxlength="15"
                      maxtags="3"
                      ellipsis
                      placeholder="Input most 3 tags which maximum is 15 words"
              />
              <el-row style="margin-bottom:10px ; margin-top:25px">
                <el-button size="mini" @click = "addTags(1)" round>Study Abroad</el-button>
                <el-button size="mini" @click = "addTags(2)" round>Academic source</el-button>
                <el-button size="mini" @click = "addTags(3)" round>Internship</el-button>
              </el-row>
              <el-row style="margin-bottom:10px ; margin-top:10px">
                <el-button size="mini" @click = "addTags(4)" round>Second-hand Trading</el-button>
                <el-button size="mini" @click = "addTags(5)" round>Lost and Found</el-button>
<!--                <el-button size="mini" @click = "addTags(6)" round>Entertainment</el-button>-->
              </el-row>
              <el-form-item>
                <el-button
                        type="primary"
                        @click="submitForm('ruleForm')"
                >Create
                </el-button>
                <el-button @click="resetForm('ruleForm')">Reset</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {post} from "../../api/post";

export default {
  name: 'postCard',
  data() {
    return {
      token: '',
      dialogVisible: false,
      ruleForm: {
        title: '', // 标题
        tags: [], // 标签
        content: '' // 内容
      },
      rules: {
        title: [
          { required: true, message: 'Input topic of conversation', trigger: 'blur' },
          {
            min: 1,
            max: 25,
            message: '1 to 25 words allowed',
            trigger: 'blur'
          }
        ]
      },
    }
  },
  methods:{
    addTags(index){
      if(index == 1) {
        if (this.ruleForm.tags.length < 3 && !this.ruleForm.tags.includes("studyabroad")) {
          this.ruleForm.tags.push("studyabroad");
        }
      }
      if(index == 2) {
        if (this.ruleForm.tags.length < 3 && !this.ruleForm.tags.includes("academic")) {
          this.ruleForm.tags.push("academic");
        }
      }
      if(index == 3) {
        if (this.ruleForm.tags.length < 3 && !this.ruleForm.tags.includes("internship")) {
        this.ruleForm.tags.push("internship");
      }
      }
      if(index == 4) {
        if (this.ruleForm.tags.length < 3 && !this.ruleForm.tags.includes("secondhand")) {
          this.ruleForm.tags.push("secondhand");
        }
      }
      if(index == 5) {
        if (this.ruleForm.tags.length < 3 && !this.ruleForm.tags.includes("lostandfound")) {
          this.ruleForm.tags.push("lostandfound");
        }
      }
      // if(index == 6){
      //   this.ruleForm.tags.push("entertainment");
      // }
      },
    open() {
      this.$alert('You need to log in before posting！', '', {
        confirmButtonText: 'Confirm',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${ action }`
          });
          this.$router.push({path: "/"});
        }
      });
    },
    toPostCreate(){
      if(this.token !== "null" && this.token !== ''){
        this.dialogVisible = true;
      }
      else{
        this.open();
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (
                  this.ruleForm.content.length === 1 ||
                  this.ruleForm.content == null ||
                  this.ruleForm.content === ''
          ) {
            alert('Content cant be empty')
            return false
          }
          if (this.ruleForm.tags == null || this.ruleForm.tags.length === 0) {
            alert('tags cant be empty')
            return false
          }
          //this.ruleForm.content = this.contentEditor.getValue()
          post(this.ruleForm).then((response) => {
            const { data } = response;
            location.reload();
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.ruleForm.content = ''
      this.ruleForm.tags = ''
    },
  },
  created(){
    //页面加载时就从本地通过localstorage获取存储的token值
    this.token = localStorage.getItem('token')
  },
}
</script>

<style scoped>
</style>
