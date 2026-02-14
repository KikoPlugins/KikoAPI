import {Theme} from 'vitepress';
import DefaultTheme from "vitepress/theme";
import './style/index.css';
import CachedImage from './components/CachedImage.vue';

export default {
    extends: DefaultTheme,
    enhanceApp({ app }) {
        // Register global components
        app.component('CachedImage', CachedImage);
    }
} satisfies Theme;

