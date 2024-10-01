import {defaultTheme} from '@vuepress/theme-default'
import {defineUserConfig} from 'vuepress/cli'
import {viteBundler} from '@vuepress/bundler-vite'
import {mdEnhancePlugin} from 'vuepress-plugin-md-enhance'

export default defineUserConfig({
    lang: 'es-ES',
    base: '/GestionHotel-Suite/',

    title: 'GestiónHotel Suite',
    // description: 'My first VuePress Site',

    theme: defaultTheme({
        version: '1.0.0',
        // logo: 'https://vuejs.press/images/hero.png',
        repo: 'https://github.com/cocot3ro/GestionHotel-Suite',
        editLink: false,
        navbar: [
            {
                text: 'Suite',
                link: '/'
            },
            {
                text: 'Core',
                link: '/core/'
            },
            {
                text: 'Modulo Almacen',
                prefix: '/modulo-almacen/',
                children: [
                    {
                        text: 'Escritorio',
                        link: 'desktop/'
                    },
                    {
                        text: 'Android',
                        link: 'android/'
                    }
                ]
            }
        ],
        sidebar: false
    }),

    pagePatterns: [
        '**/*.md',
        '!.vuepress',
        '!node_modules',

        '!**/_*.md'
    ],

    bundler: viteBundler(),

    plugins: [
        mdEnhancePlugin({
            include: {
                deep: true
            }
        })
    ],

    markdown: {
        anchor: false
    }
})
