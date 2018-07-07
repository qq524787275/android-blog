import React from 'react';
import {StyleSheet, Text, View, AppRegistry, Button} from 'react-native';
import Home from './src/pages/Home.js';
AppRegistry.registerComponent('Home', () => Home);

export default class App extends React.Component {

    state={
        info:"登天路，踏歌行，弹指遮天!!~",
    }

    onPressLearnMore=()=>{
        this.setState({
            ...this.state,
            info:"哈哈",
        });
    }

    render() {
        return (
            <View style={styles.container}>
                <Text>哈哈!</Text>
                <Text>测试啊.</Text>
                <Text>{this.state.info}</Text>
                <Button title="什么鬼" onPress={this.onPressLearnMore}/>
                <Text>测试恩爱</Text>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
});
