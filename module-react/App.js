import React from 'react';
import {AppRegistry, NativeModules, YellowBox} from 'react-native';
import Home from './src/pages/Home.js';

YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated', 'Module RCTImageLoader']);
AppRegistry.registerComponent('Home', () => Home);
