import React, {PropTypes} from 'react';
import { connect } from 'react-redux';
import { redirectToLogin, saveRouteToBackRedirect } from './redirect';

const mapStateToProps = (state, ownProps) => ({
  authentication: state.authentication,
  location: ownProps.location.pathname
});
const mapDispatchToProps = {
  redirectToLogin,
  saveRouteToBackRedirect
};

const privateRoute = Wrapped => connect(mapStateToProps, mapDispatchToProps)(class extends React.Component {

  static propTypes = {
    authentication: PropTypes.object,
    redirectToLogin: PropTypes.func,
    saveRouteToBackRedirect: PropTypes.func,
    location: PropTypes.string
  };

  componentDidMount() {
    this.redirectIfNotLogged(this.props);
  }

  componentWillReceiveProps(nextProps) {
    this.redirectIfNotLogged(nextProps);
  }

  componentWillUnmount() {
    if (!this.props.authentication.user) {
      this.props.saveRouteToBackRedirect(this.props.location);
    }
  }

  redirectIfNotLogged(props) {
    const {loading, user} = props.authentication;
    if (loading === false && !user) {
      this.props.redirectToLogin();
    }
  }

  render() {
    const {loading, user} = this.props.authentication;
    if (loading || !user) {
      return (
        <div className="center loader">
          <div>Loading...</div>
        </div>
      );
    }

    return <Wrapped {...this.props} />;
  }
});

export default privateRoute;