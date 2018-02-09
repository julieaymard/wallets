// into the model
import {Wallet} from "./wallet";

export class User{

name: string;
id: number;

//protip : always better to initiate an empty array
wallets : Wallet[]=[]

}
