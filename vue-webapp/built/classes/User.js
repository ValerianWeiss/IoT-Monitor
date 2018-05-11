var User = /** @class */ (function () {
    function User(userToken, username, password) {
        this._userToken = userToken;
        this._username = username;
        this._password = password;
    }
    Object.defineProperty(User.prototype, "userToken", {
        get: function () {
            return this._userToken;
        },
        set: function (token) {
            if (token == undefined) {
                throw new TypeError("tried to set usertoken to undefined");
            }
            this._userToken = token;
        },
        enumerable: true,
        configurable: true
    });
    return User;
}());
export default User;
//# sourceMappingURL=User.js.map