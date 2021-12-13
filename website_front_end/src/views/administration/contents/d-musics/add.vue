<template>
  <div id="add_drawer_pane">
    <Button @click="drawerOpen = true" type="success" style="margin-right: 8px">添加（ADD）</Button>
    <Drawer
        title="音乐信息添加"
        v-model="drawerOpen"
        width="720"
        :mask-closable="false"
        :styles="styles"
    >
      <Form :model="formData">
        <Row :gutter="32">
          <Col span="8">
            <img-upload :imgUrl="formData.coverUrl" @callBack="callBack1">
              <template v-slot:icon-content>
                <i class="el-icon el-icon-mic"></i>
                <div class="el-upload__text">歌曲专辑封面上传</div>
              </template>
            </img-upload>
          </Col>
          <Col span="8">
            <img-upload :imgUrl="formData.coverUrl" @callBack="callBack2">
              <template v-slot:icon-content>
                <i class="el-icon el-icon-picture-outline"></i>
                <div class="el-upload__text">歌手封面上传</div>
              </template>
            </img-upload>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="歌曲名" label-position="top">
              <Input v-model="formData.name" placeholder="歌曲名称"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="专辑" label-position="top">
              <Input v-model="formData.album" placeholder="歌曲专辑"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="类型" label-position="top">
              <Select v-model="formData.type" placeholder="选择歌曲类型">
                <Option value="内地流行">内地流行</Option>
                <Option value="港澳台流行">港澳台流行</Option>
                <Option value="国际流行">国际流行</Option>
                <Option value="粤语">粤语</Option>
                <Option value="英语">英语</Option>
                <Option value="日语动漫">日语动漫</Option>
                <Option value="舞曲">舞曲</Option>
                <Option value="怀旧">怀旧</Option>
                <Option value="摇滚">摇滚</Option>
                <Option value="组合">组合</Option>
                <Option value="乡村">乡村</Option>
                <Option value="电音/重金属">电音/重金属</Option>
                <Option value="古典音乐">古典音乐</Option>
                <Option value="电影原声">电影原声</Option>
                <Option value="其他">其他</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="演唱者" label-position="top">
              <Input v-model="formData.singer" placeholder="歌曲演唱者"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="作词人" label-position="top">
              <Input v-model="formData.writeWords" placeholder="歌曲作词人"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="作曲人" label-position="top">
              <Input v-model="formData.compose" placeholder="歌曲作曲人"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="8">
            <FormItem label="发行地区" label-position="top">
              <Input v-model="formData.country" placeholder="歌曲发行地区"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="发行年份" label-position="top">
              <Input v-model="formData.releaseYear" type="number" placeholder="歌曲发行年份"/>
            </FormItem>
          </Col>
          <Col span="8">
            <FormItem label="长度（秒）" label-position="top">
              <Input v-model="formData.minutes" type="number" placeholder="歌曲长度/秒"/>
            </FormItem>
          </Col>
        </Row>
        <Row :gutter="32">
          <Col span="24">
            <FormItem label="歌词" label-position="top">
              <Input v-model="formData.lyric" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="歌曲词"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
      <div class="demo-drawer-footer">
        <Button style="margin-right: 8px" @click="drawerOpen = false">取消</Button>
        <Button type="primary" @click="add()">添加</Button>
      </div>
    </Drawer>
  </div>
</template>

<script>
import imgUpload from '../../../../components/img-upload'

export default {
  name: "add",
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
        name: null,
        album: null,
        singer: null,
        writeWords: null,
        compose: null,
        lyric: null,
        type: null,
        country: null,
        minutes: null,
        releaseYear: null,
        albumAvatarUrl: null,
        singerAvatarUrl: null
      }
    }
  },
  components: {imgUpload},
  methods: {
    add() {
      console.log(this.formData);
      for (let key in this.formData) {
        if (this.formData[key] == null || this.formData[key] === '') {
          this.notNull = false
          break;
        } else {
          this.notNull = true
        }
      }
      if (this.notNull) {
        this.api.dMusicAdd(this.formData).then(rs => {
          if (rs.data.data === 1) {
            this.$message({
              type: 'success',
              message: '添加成功!'
            });
            location.reload();
          } else if (rs.data.data === -1) {
            this.$message.error('该电影信息已存在，添加失败!');
          } else {
            this.$message.error('添加失败!');
          }
        }).catch(err => {
          this.$message.error('添加失败!');
          console.log(err);
        });
        this.drawerOpen = false
      } else {
        this.$message.warning('添加失败，数据信息不完善!');
      }
    },
    callBack1(imgUrl){
      this.formData.albumAvatarUrl = imgUrl
    },
    callBack2(imgUrl){
      this.formData.singerAvatarUrl = imgUrl
    }
  }
}
</script>

<style scoped>
#add_drawer_pane {
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
