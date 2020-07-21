/* Amplify Params - DO NOT EDIT
	FUNCTION_JAVASAMPLE_NAME
Amplify Params - DO NOT EDIT */
/** @typedef {import('aws-sdk')} IAWS */

/** @type {IAWS} */
const AWS = require('aws-sdk');

const lambda = new AWS.Lambda({ region: 'us-east-1' });

exports.handler = async (event) => {
    console.log(JSON.stringify(event,null,2));

    const result = await lambda
      .invoke({
        FunctionName: process.env.FUNCTION_JAVASAMPLE_NAME,
        // FunctionName: `javasample-${
        //   process.env.ENV ? `${process.env.ENV}` : 'devopme'
        // }`,
        InvocationType: 'RequestResponse',
        Payload: JSON.stringify({
            "key": "google.pdf",
            "metadatas": {
                "2_16_76_1_12_1_1": "",
                "2_16_76_1_4_2_2_1" : "20325",
                "2_16_76_1_4_2_2_2" : "SP"
            }
        }),
      })
      .promise();
    // TODO implement
    const response = {
        statusCode: 200,
        body: result.Payload,
    };
    return response;
};
