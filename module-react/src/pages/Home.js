import React from 'react';
import FlatList from './FlatList';
import SectionList from './SectionList';
import Main from './Main';
import App from './../pages/app/app'
import {createStackNavigator} from 'react-navigation';
import Color from 'color';

export default class Home extends React.Component {

    render() {
        let colorPrimary="#000000"
        if(this.props.primary!==undefined){
            colorPrimary= this.props.primary;
        }
        RootStack = createStackNavigator(
            {
                App:{screen:App},
                FlatList: {screen: FlatList},
                SectionList: {screen: SectionList},
                Main: {screen: Main},
            },
            {
                initialRouteName: 'Main',
                navigationOptions: {
                    gesturesEnabled: true,
                    headerTintColor: Color(colorPrimary).isLight() ? "#000000" : "#ffffff",
                    headerStyle: {
                        backgroundColor: colorPrimary,
                    },
                    headerTitleStyle: {
                        fontWeight: 'bold',
                    },
                    headerMode: "float",
                    primaryColor: colorPrimary,
                },
                cardStyle: {
                    backgroundColor: "transparent",
                },

            }
        );
        {
            console.log("执行了啊执行了啊执行了啊执行了啊执行了啊执行了啊执行了啊")
        }
        return <RootStack></RootStack>
    }
};

