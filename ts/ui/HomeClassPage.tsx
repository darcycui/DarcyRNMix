import { Component } from "react";
import { Text, View } from "react-native";

export default class HomeClassPage extends Component {
  render() {
    return (
      <View
        style={{
          flex: 1,
          justifyContent: "center",
          alignItems: "center",
          alignContent: "center",
        }}
      >
        <Text>"Class组件"</Text>
        <Text>"HomeClassPage"</Text>
      </View>
    );
  }
}
