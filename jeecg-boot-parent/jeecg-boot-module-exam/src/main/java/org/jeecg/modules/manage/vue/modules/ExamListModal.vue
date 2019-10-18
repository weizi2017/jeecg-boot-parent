<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="考试名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'examName', validatorRules.examName]" placeholder="请输入考试名称"></a-input>
        </a-form-item>
          
        <a-form-item label="考试开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择考试开始时间" v-decorator="[ 'examStartTime', validatorRules.examStartTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="考试结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择考试结束时间" v-decorator="[ 'examEndTime', validatorRules.examEndTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="题库名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['bankName']" :trigger-change="true" dictCode="exam_bank,exam_name,id" placeholder="请选择题库名称"/>
        </a-form-item>
          
        <a-form-item label="单选题数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'singleChoiceNum', validatorRules.singleChoiceNum]" placeholder="请输入单选题数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="单选题分数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'singleChoiceMark', validatorRules.singleChoiceMark]" placeholder="请输入单选题分数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="多选题数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'multipleChoiceNum', validatorRules.multipleChoiceNum]" placeholder="请输入多选题数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="多选题分数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'multipleChoiceMark', validatorRules.multipleChoiceMark]" placeholder="请输入多选题分数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="判断题数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'judgmentNum', validatorRules.judgmentNum]" placeholder="请输入判断题数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="判断题分数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'judgmentMark', validatorRules.judgmentMark]" placeholder="请输入判断题分数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="及格分数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'examPassMark', validatorRules.examPassMark]" placeholder="请输入及格分数" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="总分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'examTotalMark', validatorRules.examTotalMark]" placeholder="请输入总分" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="考试说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['examExplain']" rows="4" placeholder="请输入考试说明"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "ExamListModal",
    components: { 
      JDate,
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
        examName:{},
        examStartTime:{},
        examEndTime:{},
        bankName:{},
        singleChoiceNum:{},
        singleChoiceMark:{},
        multipleChoiceNum:{},
        multipleChoiceMark:{},
        judgmentNum:{},
        judgmentMark:{},
        examPassMark:{},
        examTotalMark:{},
        examExplain:{},
        },
        url: {
          add: "/manage/examList/add",
          edit: "/manage/examList/edit",
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
          this.form.setFieldsValue(pick(this.model,'examName','examStartTime','examEndTime','bankName','singleChoiceNum','singleChoiceMark','multipleChoiceNum','multipleChoiceMark','judgmentNum','judgmentMark','examPassMark','examTotalMark','examExplain'))
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
        this.form.setFieldsValue(pick(row,'examName','examStartTime','examEndTime','bankName','singleChoiceNum','singleChoiceMark','multipleChoiceNum','multipleChoiceMark','judgmentNum','judgmentMark','examPassMark','examTotalMark','examExplain'))
      }
      
    }
  }
</script>