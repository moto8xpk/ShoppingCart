import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MiddleContentComponent } from './middle-content/middle-content.component';
import { TopMiddleContentComponent } from './top-middle-content/top-middle-content.component';
import { BotMiddleContentComponent } from './bot-middle-content/bot-middle-content.component';
import { RegisterComponent } from './register/register.component';
import { CompareValidatorDirective } from './compare-validator.directive';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    MiddleContentComponent,
    TopMiddleContentComponent,
    BotMiddleContentComponent,
    RegisterComponent,
    CompareValidatorDirective
  ],
  exports:[
    MiddleContentComponent,
    RegisterComponent
  ]
})
export class MiddleModule { }
