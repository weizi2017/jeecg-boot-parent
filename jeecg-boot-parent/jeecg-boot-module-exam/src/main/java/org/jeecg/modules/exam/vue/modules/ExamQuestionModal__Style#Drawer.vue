<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="题目类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['quesType']" :trigger-change="true" dictCode="exam_type" placeholder="请选择题目类型"/>
        </a-form-item>
        <a-form-item label="题目内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['quesContent']" rows="4" placeholder="请输入题目内容"/>
        </a-form-item>
        <a-form-item label="题目图片1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesImg1']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="题目图片2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesImg2']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="题目图片3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesImg3']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="题目图片4" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesImg4']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="文字选项1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesCheck1', validatorRules.quesCheck1]" placeholder="请输入文字选项1"></a-input>
        </a-form-item>
        <a-form-item label="文字选项2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesCheck2', validatorRules.quesCheck2]" placeholder="请输入文字选项2"></a-input>
        </a-form-item>
        <a-form-item label="文字选项3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesCheck3', validatorRules.quesCheck3]" placeholder="请输入文字选项3"></a-input>
        </a-form-item>
        <a-form-item label="文字选项4" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesCheck4', validatorRules.quesCheck4]" placeholder="请输入文字选项4"></a-input>
        </a-form-item>
        <a-form-item label="文字选项5" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesCheck5', validatorRules.quesCheck5]" placeholder="请输入文字选项5"></a-input>
        </a-form-item>
        <a-form-item label="图片选项1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesCheckImg1']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="图片选项2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesCheckImg2']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="图片选项3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesCheckImg3']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="图片选项4" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesCheckImg4']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="图片选项5" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['quesCheckImg5']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="答案" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesAnswer', validatorRules.quesAnswer]" placeholder="请输入答案"></a-input>
        </a-form-item>
        <a-form-item label="答案解析" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['quesKey']" rows="4" placeholder="请输入答案解析"/>
        </a-form-item>
        <a-form-item label="题目分值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'quesMark', validatorRules.quesMark]" placeholder="请输入题目分值"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "ExamQuestionModal",
    components: { 
      JUpload,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        quesType:{rules: [{ required: true, message: '请输入题目类型!' }]},
        quesContent:{rules: [{ required: true, message: '请输入题目内容!' }]},
        quesImg1:{},
        quesImg2:{},
        quesImg3:{},
        quesImg4:{},
        quesCheck1:{},
        quesCheck2:{},
        quesCheck3:{},
        quesCheck4:{},
        quesCheck5:{},
        quesCheckImg1:{},
        quesCheckImg2:{},
        quesCheckImg3:{},
        quesCheckImg4:{},
        quesCheckImg5:{},
        quesAnswer:{rules: [{ required: true, message: '请输入答案!' }]},
        quesKey:{},
        quesMark:{rules: [{ required: true, message: '请输入题目分值!' }]},
        },
        url: {
          add: "/exam/examQuestion/add",
          edit: "/exam/examQuestion/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'quesType','quesContent','quesImg1','quesImg2','quesImg3','quesImg4','quesCheck1','quesCheck2','quesCheck3','quesCheck4','quesCheck5','quesCheckImg1','quesCheckImg2','quesCheckImg3','quesCheckImg4','quesCheckImg5','quesAnswer','quesKey','quesMark'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'quesType','quesContent','quesImg1','quesImg2','quesImg3','quesImg4','quesCheck1','quesCheck2','quesCheck3','quesCheck4','quesCheck5','quesCheckImg1','quesCheckImg2','quesCheckImg3','quesCheckImg4','quesCheckImg5','quesAnswer','quesKey','quesMark'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>