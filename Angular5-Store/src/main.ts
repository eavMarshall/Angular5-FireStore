/// <reference types="node" />

import { enableProdMode } from '@angular/core'
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic'

import { AppModule } from './app/app.module'
import { environment } from './environments/environment'

import { hmrBootstrap } from './hmr'

declare var module: any;

if (environment.production) {
  enableProdMode();
} else {
  if (module.hot) {
    module.hot.accept()
  }
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.log(err))