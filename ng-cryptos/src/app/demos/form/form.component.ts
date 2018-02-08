import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {
  formModel = {
    name: '',
    limit: 9000
    level: 'Noob - 0 -'
  };

  levels = ['Noob', 'Expert', 'Master'];

  constructor() {
  }

  ngOnInit() {
  }

  createNewWallet() {
    console.log(this.formModel);
  }

  isDisabled() {
    if (this.formModel.name) return false;
    return true;
  }
}
