<template>
  <span id="update_drawer_pane">
    <Button style="margin-right: 5px" type="success" size="small" @click="drawerOpen = true">修改
     <Icon type="md-create"/>
    </Button>
  <Drawer
      :title="'电影信息修改（编号：'+formData.id+'）'"
      v-model="drawerOpen"
      width="720"
      :mask-closable="false"
      :styles="styles"
  >
    <Form :model="formData">
        <Row :gutter="32">
          <Col span="12">
            <img-upload :imgUrl="formData.coverUrl" @callBack="callBack">
               <template v-slot:icon-content>
                <i class="el-icon el-icon-film"></i>
                <div class="el-upload__text">将电影封面拖到此处或<br>点击上传</div>
              </template>
            </img-upload>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="电影名" label-position="top">
              <Input v-model="formData.name" placeholder="电影名称"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="别名" label-position="top">
              <Input v-model="formData.alternateName" placeholder="电影别名"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="题材类型" label-position="top">
              <Select v-model="formData.type" placeholder="选择电影题材类型">
                <Option value="爱情">爱情</Option>
                <Option value="喜剧">喜剧</Option>
                <Option value="动作">动作</Option>
                <Option value="剧情">剧情</Option>
                <Option value="科幻">科幻</Option>
                <Option value="恐怖">恐怖</Option>
                <Option value="惊悚">惊悚</Option>
                <Option value="悬疑">悬疑</Option>
                <Option value="犯罪">犯罪</Option>
                <Option value="战争">战争</Option>
                <Option value="动画">动画</Option>
                <Option value="其他">其他</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="制片地区" label-position="top">
              <Input v-model="formData.country" placeholder="电影制片地区"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="发行日期" label-position="top">
              <DatePicker v-model="formData.releaseDate" type="date" placeholder="电影发行日期" style="display: block"
                          placement="bottom-end"></DatePicker>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="时长（分钟）" label-position="top">
              <Input v-model="formData.minutes" type="number" placeholder="电影时长/分钟"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="导演" label-position="top">
              <Input v-model="formData.director" placeholder="电影导演"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="编剧" label-position="top">
              <Input v-model="formData.writer" placeholder="电影编剧"/>
            </FormItem>
          </Col>
          <Col span="8">
          <FormItem label="演员表" label-position="top">
            <Input v-model="formData.actors" placeholder="电影演员表"/>
          </FormItem>
        </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="电影简介" label-position="top">
              <Input v-model="formData.introduction" type="textarea" :autosize="{minRows: 3,maxRows: 3}"
                     placeholder="电影简介"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawerOpen = false">取消</Button>
        <Button type="primary" @click="update()">修改</Button>
      </div>
    </Drawer>
  </span>
</template>

<script>
import imgUpload from '../../../../components/img-upload'

export default {
  name: "update",
  props: {
    optionData: {
      type: Object,
      default() {
        return {}
      },
      required: true//要求必须赋值
    }
  },
  data() {
    return {
      drawerOpen: false,
      styles: {
        height: 'calc(100% - 55px)',
        overflow: 'auto',
        paddingBottom: '53px',
        position: 'static'
      },
      formData: {
        id: this.optionData.id,
        name: this.optionData.name,
        type: this.optionData.type,
        country: this.optionData.country,
        releaseDate: this.optionData.releaseDate,
        director: this.optionData.director,
        writer: this.optionData.writer,
        actors: this.optionData.actors,
        alternateName: this.optionData.alternateName,
        minutes: this.optionData.minutes,
        introduction: this.optionData.introduction,
        coverUrl: this.optionData.coverUrl
      },
      notNull: false
    }
  },
  components: {imgUpload},
  methods: {
    update() {
      for (let key in this.formData) {
        if (this.formData[key] == null || this.formData[key] === '') {
          this.notNull = false
          break;
        } else {
          this.notNull = true
        }
      }
      if (this.notNull) {
        this.api.dMovieUpdate(this.formData).then(rs => {
          if (rs.data.data) {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            location.reload();
          } else {
            this.$message.error('修改失败!');
          }
        }).catch(err => {
          this.$message.error('修改失败!');
          console.log(err);
        });
        this.drawerOpen = false
      } else {
        this.$message.warning('修改失败，数据信息不完善!');
      }
    },
    callBack(imgUrl) {
      this.formData.coverUrl = imgUrl
    },
  }
}
</script>

<style scoped>
#update_drawer_pane {
  width: auto;
  height: auto;
}

/deep/ .el-icon{
  font-size: 67px;
  color: #C0C4CC;
  margin: 40px 50px 20px;
  line-height: 50px;
}
</style>
