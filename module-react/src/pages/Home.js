import React from 'react';
import {
    StyleSheet,
    Text,
    View,
    Button,
    Image,
} from 'react-native';
import logo from './../assets/img/new_logo.png';

export default class HelloWorld extends React.Component {
    state = {
        info1: "登天路，踏歌行，弹指遮天！！！",
        info2: "我是彩蛋"
    }

    click = () => {
        this.setState({
            ...this.state,
            info2: "舍我其谁啊啊"
        });
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.hello}>{this.state.info1}</Text>
                <Text style={styles.hello}>{this.state.info2}</Text>
                <Button title="按钮触发彩蛋" onPress={this.click} style={{width: 50}}/>
                <View style={{flexDirection: 'row', alignItems: 'center',}}>
                    <Text>react项目中的图片</Text>
                    <Image source={logo} style={{width: 50, height: 50}}/>
                </View>
                <View style={{flexDirection: 'row', alignItems: 'center',}}>
                    <Text>drawable中的图片</Text>
                    <Image source={{uri: 'icon_question'}} style={{width: 40, height: 40}}/>
                </View>
                <View style={{flexDirection: 'row', alignItems: 'center',}}>
                    <Text>mipmap中的图片</Text>
                    <Image source={{uri: 'mipmap/ic_pikachu'}} style={{width: 40, height: 40}}/>
                </View>
            </View>
        )
    }
}
var styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
    },
    hello: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
});
