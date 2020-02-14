import React, { Component } from 'react';
import { connect } from 'react-redux';
import Chart from '../components/chart';

class BitcoinList extends Component {
  

  renderBitcoin(Data){


    console.log(typeof Data)
    let name = Data[0].date
    console.log(name)
 
    const date = Data.map(bitcoin => bitcoin.date)
    const high = Data.map(bitcoin => bitcoin.high)
    const low = Data.map(bitcoin => bitcoin.low)
    console.log(date)
    console.log(high)

    return(
      <tr key = {name}>
      <th>{name}</th>
      {/* <th><Chart data={date} color='orange'/></th> */}
      <th><Chart data={high} color='green'/></th>
      <th><Chart data={low} color='blue'/></th>
    </tr>

    )
      }
        

  render() {
    return (
      <table className="table">
        <thead>
          <tr>
            <th>name</th>
            <th>high</th>
            <th>low</th>

          </tr>
        </thead>
        <tbody>
         {this.props.bitcoin.map(this.renderBitcoin)}
   
        </tbody>
      </table>
    );
  }
}

// mapSrtateToProps funciton
function mapSrtateToProps(state){
  return{ bitcoin: state.bitcoin};
}

// connect mapping
export default connect(mapSrtateToProps)(BitcoinList);