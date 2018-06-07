import Router from '../../router';
import Store from '../../store';

export default class ErrorCause {
    public errorMessage: string;
    public errorCode: number;
}

export function onHttpConnectionError(error: any) {
    Router.push('/error');
    console.log(error);
    Store.commit('setHeading', 'error');  
}
